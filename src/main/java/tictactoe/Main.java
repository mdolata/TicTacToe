package tictactoe;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cells = scanner.nextLine().replace("\"", "");
        Field field = Field.fromCells(cells);
        System.out.println(field.getPrintableField());
        System.out.println(FieldValidator.validate(field).getName());
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

        private Field(String[][] array) {
            this.array = array;
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

        public boolean isSymbolWin(String symbol) {
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

        public boolean isDraw() {
            return !isSymbolWin("X") && !isSymbolWin("O") && noMoreMoves();
        }

        private boolean noMoreMoves() {
            return Arrays.binarySearch(array[0], " ") < 0 &&
                    Arrays.binarySearch(array[1], " ") < 0 &&
                    Arrays.binarySearch(array[2], " ") < 0;
        }
    }

    static class FieldValidator {
        static State validate(Field field) {
            if (isImpossible(field)) {
                return State.IMPOSSIBLE;
            } else if (isSymbolWin(field, "X") &&
                    isSymbolWin(field, "O")) {
                return State.IMPOSSIBLE;
            } else if (isSymbolWin(field, "X")) {
                return State.X_WINS;
            } else if (isSymbolWin(field, "O")) {
                return State.O_WINS;
            } else if (isDraw(field)) {
                return State.DRAW;
            } else if (isGameNotFinished(field)) {
                return State.GAME_NOT_FINISHED;
            }
            return State.UNKNOWN;
        }

        private static boolean isGameNotFinished(Field field) {
            return !field.noMoreMoves();
        }

        private static boolean isDraw(Field field) {
            return field.isDraw();
        }

        private static boolean isSymbolWin(Field field, String symbol) {
            return field.isSymbolWin(symbol);
        }

        private static Boolean isImpossible(Field field) {
            int quantityOfX = field.countSymbol("X");
            int quantityOfY = field.countSymbol("O");

            return Math.abs(quantityOfX - quantityOfY) > 1;
        }
    }
}
