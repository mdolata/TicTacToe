package tictactoe;

import java.util.*;
import java.util.stream.Collectors;

import static tictactoe.Main.State.*;

public class Main {
    public static void main(String[] args) {
        Player humanPlayer = new HumanPlayer("X");
        Player easyBotPlayer = new EasyBotPlayer("O");

        Player[] players = new Player[] {humanPlayer, easyBotPlayer};
        GameLoop gameLoop = new GameLoop(players);
        State run = gameLoop.run();

        System.out.println(run);

    }

    static class GameLoop {
        private final Player[] players;
        private int moveCount;
        private Field field;

        GameLoop(Player[] players) {
            this.players = players;
            moveCount = 0;
            field = Field.fromCells("         ");
        }

        State run () {
            Player currentPlayer;
            do {
                if (moveCount % 2 == 0) {
                    currentPlayer = players[0];
                } else {
                    currentPlayer = players[1];
                }

                System.out.println(currentPlayer.moveMessage());
                Either<String, Field> nextField = currentPlayer.nextMove(field);
                if (nextField.isRight()) {
                    moveCount++;
                    field = nextField.getRight();
                    System.out.println(field.getPrintableField());
                } else {
                    System.out.println(nextField.getLeft());
                }
            } while (! field.getState().isTerminal());

            return field.getState();
        }

        int getMoveCount() {
            return moveCount;
        }

        Field getField() {
            return field;
        }
    }

    enum State {
        GAME_NOT_FINISHED("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins"),
        IMPOSSIBLE("Impossible"),
        UNKNOWN("Unknown");

        private final String name;
        private static final List<State> terminalStates = new ArrayList<>();

        static {
            terminalStates.add(State.DRAW);
            terminalStates.add(State.O_WINS);
            terminalStates.add(State.X_WINS);
        }

        State(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isTerminal() {
            return terminalStates.contains(this);
        }
    }

    static class Field {
        private final String[][] array;
        private final String cells;
        private final String winner;
        private final State state;
        private final boolean isTwoWinners;
        private final Map<Coordinates, Integer> coordinateMapping;
        private final List<Coordinates> possibleMoves;

        private Field(String[][] array, String cells) {
            this.array = array;
            this.cells = cells;
            this.coordinateMapping = createCoordinateMapping();
            this.possibleMoves = createPossibleMoves();
            this.winner = calculateWinner();
            this.isTwoWinners = this.winner.equals("I");
            this.state = validate();
        }

        private List<Coordinates> createPossibleMoves() {
            return coordinateMapping.keySet()
                    .stream()
                    .filter(coordinates -> cells.charAt(coordinateMapping.get(coordinates)) == ' ')
                    .collect(Collectors.toList());
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

        State getState() {
            return state;
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

        Either<String, Field> nextMove(String coordinates, String nextSymbol) {
            char[] chars = cells.toCharArray();
            Either<String, Coordinates> coordinatesEither = Coordinates.fromString(coordinates);
            if (coordinatesEither.isLeft()) {
                return Either.left(coordinatesEither.getLeft());
            }

            Integer orDefault = coordinateMapping.getOrDefault(coordinatesEither.getRight(), -1);

            if (orDefault < 0) {
                return Either.left("Coordinates should be from 1 to 3!");
            } else if (!possibleMoves.contains(coordinatesEither.getRight())) {
                return Either.left("This cell is occupied! Choose another one!");
            } else {
                chars[orDefault] = nextSymbol.charAt(0);
            }

            return Either.right(Field.fromCells(String.valueOf(chars)));
        }

        private Map<Coordinates, Integer> createCoordinateMapping(){
            Map<Coordinates, Integer> map = new HashMap<>(9);
            map.put(Coordinates.fromString("1 1").getRight(), 6);
            map.put(Coordinates.fromString("1 2").getRight(), 3);
            map.put(Coordinates.fromString("1 3").getRight(), 0);
            map.put(Coordinates.fromString("2 1").getRight(), 7);
            map.put(Coordinates.fromString("2 2").getRight(), 4);
            map.put(Coordinates.fromString("2 3").getRight(), 1);
            map.put(Coordinates.fromString("3 1").getRight(), 8);
            map.put(Coordinates.fromString("3 2").getRight(), 5);
            map.put(Coordinates.fromString("3 3").getRight(), 2);
            return map;
        }

        private String calculateWinner() {
            boolean isXWin = isSymbolWin("X");
            boolean isOWin = isSymbolWin("O");

            if (isXWin && isOWin) {
                return "I";
            } else if (isXWin) {
                return "X";
            } else if (isOWin) {
                return "O";
            }

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
            return possibleMoves.size() == 0;
        }

        private Boolean isImpossibleState() {
            int quantityOfX = countSymbol("X");
            int quantityOfY = countSymbol("O");

            boolean isTooMuchSymbol = Math.abs(quantityOfX - quantityOfY) > 1;
            boolean isThereTwoWinners = (isSymbolXWin() && isSymbolOWin());

            return isTooMuchSymbol || isThereTwoWinners;
        }

        List<Coordinates> getPossibleMoves() {
            return possibleMoves;
        }
    }

    private static class Coordinates {

        private final String coordinates;
        private final Integer x;
        private final Integer y;
        private Coordinates(String coordinates, int x, int y) {
            this.coordinates = coordinates;
            this.x = x;
            this.y = y;
        }

        static Either<String, Coordinates> fromString(String coordinates) {
            String[] split = coordinates.split(" ");
            int x, y;
            try {
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                return Either.left("You should enter numbers!");
            }
            return Either.right(new Coordinates(coordinates, x, y));
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

    static class Either<L, R> {
        private final L left;
        private final R right;
        private final boolean isRight;

        private Either(L left, R right, boolean isRight) {
            this.left = left;
            this.right = right;
            this.isRight = isRight;
        }

        static <L,R> Either<L,R> right(R right){
            return new Either<>(null, right, true);
        }

        static <L,R> Either<L,R> left(L left){
            return new Either<>(left, null, false);
        }

        boolean isLeft() {
            return !isRight;
        }

        boolean isRight() {
            return isRight;
        }

        L getLeft() {
            return left;
        }

        R getRight() {
            return right;
        }
    }

    interface Player {
        Either<String, Main.Field> nextMove(Field field);
        String moveMessage();
    }

    static class HumanPlayer implements Player {

        private final String symbol;
        private final Scanner scanner;

        HumanPlayer(String symbol) {
            this.symbol = symbol;
            scanner = new Scanner(System.in);
        }

        @Override
        public Either<String, Field> nextMove(Field field) {
            String nextCoordinates = scanner.nextLine();
            return field.nextMove(nextCoordinates, symbol);
        }

        @Override
        public String moveMessage() {
            return "Enter the coordinates: ";
        }
    }

    static class EasyBotPlayer implements Player {

        private final Random random;
        private final String symbol;

        EasyBotPlayer(String symbol) {
            this.symbol = symbol;
            random = new Random();
        }

        @Override
        public Either<String, Field> nextMove(Field field) {
            List<Coordinates> possibleMoves = field.getPossibleMoves();
            Coordinates nextCoordinates = possibleMoves.get(random.nextInt(possibleMoves.size()));
            Either<String, Field> nextMove = field.nextMove(nextCoordinates.coordinates, symbol);
            if (nextMove.isRight()) {
                return nextMove;
            }
            return Either.left("Something went wrong with bot player");
        }

        @Override
        public String moveMessage() {
            return "Making move level \"easy\"";
        }
    }
}
