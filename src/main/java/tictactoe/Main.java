package tictactoe;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static tictactoe.Main.State.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cells = scanner.nextLine().replace("\"", "");
        Field field = Field.fromCells(cells);
        System.out.println(field.getPrintableField());
        System.out.println(field.getStateName());
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
        private final String winner;
        private final State state;
        private final boolean isTwoWinners;

        private Field(String[][] array) {
            this.array = array;
            this.winner = calculateWinner();
            this.isTwoWinners = this.winner.equals("I");
            this.state = validate();
        }

        static Field fromCells(String cells) {
            String[][] array = new String[3][3];
            for (int i = 0, counter = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++, counter++) {
                    array[i][j] = String.valueOf(cells.charAt(counter));
                }
            }

            return new Field(array);
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
}
