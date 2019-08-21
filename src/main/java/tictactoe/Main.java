package tictactoe;

import java.util.*;

import static tictactoe.Main.State.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cells = scanner.nextLine().replace("\"", "");
        Field field = Field.fromCells(cells);
        System.out.println(field.getPrintableField());
//        System.out.println(field.getStateName());
        String nextMove = scanner.nextLine();
        System.out.println(field.nextMove(nextMove).getPrintableField());
    }

    enum State {
        GAME_NOT_FINISHED("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins"),
        IMPOSSIBLE("Impossible"),
        UNKNOWN("Unknown");

        private final String name;

        State(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static class Field {
        private final String[][] array;
        private final String cells;
        private final String winner;
        private final State state;
        private final boolean isTwoWinners;
        private final Map<Coordinates, Integer> coordinateMapping;

        private Field(String[][] array, String cells) {
            this.array = array;
            this.cells = cells;
            this.winner = calculateWinner();
            this.isTwoWinners = this.winner.equals("I");
            this.state = validate();
            coordinateMapping = createCoordinateMapping();
        }

        static Field fromCells(String cells) {
            String[][] array = new String[3][3];
            for (int i = 0, counter = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++, counter++) {
                    array[i][j] = String.valueOf(cells.charAt(counter));
                }
            }

            return new Field(array, cells);
        }

        String getStateName() {
            return this.state.getName();
        }

        String getPrintableField() {
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < 3; i++) {
                String[] split = array[i];
                String row = String.format("%s %s %s", split[0], split[1], split[2]);
                result.append(String.format("| %s |\n", row));
            }
            String horizontal = "---------";
            return String.format("%s\n%s\n%s", horizontal, result.toString().trim(), horizontal);
        }

        State validate() {
            if (isImpossibleState()) {
                return IMPOSSIBLE;
            } else if (isSymbolXWin()) {
                return X_WINS;
            } else if (isSymbolOWin()) {
                return O_WINS;
            } else if (isDraw()) {
                return DRAW;
            } else if (!noMoreMoves()) {
                return GAME_NOT_FINISHED;
            }
            return UNKNOWN;
        }

        Field nextMove(String coordinates) {
            char nextSymbol = 'X';
            char[] chars = cells.toCharArray();
            Integer orDefault = coordinateMapping.getOrDefault(new Coordinates(coordinates), -1);

            if (orDefault < 0) {
                System.out.println("error");
            } else {
                chars[orDefault] = nextSymbol;
            }

            return Field.fromCells(String.valueOf(chars));
        }

        private Map<Coordinates, Integer> createCoordinateMapping(){
            Map<Coordinates, Integer> map = new HashMap<>(9);
            map.put(new Coordinates("1 1"), 6);
            map.put(new Coordinates("1 2"), 3);
            map.put(new Coordinates("1 3"), 0);
            map.put(new Coordinates("2 1"), 7);
            map.put(new Coordinates("2 2"), 4);
            map.put(new Coordinates("2 3"), 1);
            map.put(new Coordinates("3 1"), 8);
            map.put(new Coordinates("3 2"), 5);
            map.put(new Coordinates("3 3"), 2);
            return map;
        }

        private String calculateWinner() {
            boolean isXWin = isSymbolWin("X");
            boolean isOWin = isSymbolWin("O");

            if (isXWin && !isOWin)
                return "X";
            else if (!isXWin && isOWin)
                return "O";
            else if (isXWin && isOWin)
                return "I";

            return "";
        }

        private int countSymbol(String symbol) {
            int counter = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (symbol.charAt(0) == array[i][j].charAt(0)) {
                        counter++;
                    }
                }
            }
            return counter;
        }

        private boolean isSymbolXWin() {
            return isTwoWinners || this.winner.equals("X");
        }

        private boolean isSymbolOWin() {
            return isTwoWinners || this.winner.equals("O");
        }

        private boolean isSymbolWin(String symbol) {
            // checking horizontally
            for (int i = 0; i < 3; i++) {
                String[] strings = array[i];
                if (Objects.equals(strings[0], strings[1]) &&
                        Objects.equals(strings[0], strings[2]) &&
                        Objects.equals(strings[0], symbol)) {
                    return true;
                }
            }

            // checking vertically
            for (int i = 0; i < 3; i++) {
                String[] strings = new String[]{array[0][i], array[1][i], array[2][i]};
                if (Objects.equals(strings[0], strings[1]) &&
                        Objects.equals(strings[0], strings[2]) &&
                        Objects.equals(strings[0], symbol)) {
                    return true;
                }
            }

            // checking diagonally
            if (Objects.equals(array[0][0], array[1][1]) &&
                    Objects.equals(array[0][0], array[2][2]) &&
                    Objects.equals(array[0][0], symbol)) {
                return true;
            }

            if (Objects.equals(array[2][0], array[1][1]) &&
                    Objects.equals(array[2][0], array[0][2]) &&
                    Objects.equals(array[2][0], symbol)) {
                return true;
            }

            return false;
        }

        private boolean isDraw() {
            return !isSymbolXWin() && !isSymbolOWin() && noMoreMoves();
        }

        private boolean noMoreMoves() {
            return Arrays.binarySearch(array[0], " ") < 0 &&
                    Arrays.binarySearch(array[1], " ") < 0 &&
                    Arrays.binarySearch(array[2], " ") < 0;
        }

        private Boolean isImpossibleState() {
            int quantityOfX = countSymbol("X");
            int quantityOfY = countSymbol("O");

            boolean isTooMuchSymbol = Math.abs(quantityOfX - quantityOfY) > 1;
            boolean isThereTwoWinners = (isSymbolXWin() && isSymbolOWin());

            return isTooMuchSymbol || isThereTwoWinners;
        }
    }

    private static class Coordinates {

        private final String coordinates;
        private final Integer x;
        private final Integer y;
        private Coordinates(String coordinates) {
            this.coordinates = coordinates;
            String[] split = coordinates.split(" ");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinates that = (Coordinates) o;

            if (coordinates != null ? !coordinates.equals(that.coordinates) : that.coordinates != null) return false;
            if (x != null ? !x.equals(that.x) : that.x != null) return false;
            return y != null ? y.equals(that.y) : that.y == null;
        }

        @Override
        public int hashCode() {
            int result = coordinates != null ? coordinates.hashCode() : 0;
            result = 31 * result + (x != null ? x.hashCode() : 0);
            result = 31 * result + (y != null ? y.hashCode() : 0);
            return result;
        }
    }
}
