package tictactoe.board;

import tictactoe.Main;
import tictactoe.util.Either;

import java.util.*;
import java.util.stream.Collectors;

import static tictactoe.Main.State.*;

public class Field {
    private final String[][] array;
    private final String cells;
    private final String winner;
    private final Main.State state;
    private final boolean isTwoWinners;
    private final Map<Coordinate, Integer> coordinateMapping;
    private final List<Coordinate> possibleMoves;

    private Field(String[][] array, String cells) {
        this.array = array;
        this.cells = cells;
        this.coordinateMapping = CoordinateMapping.getCoordinateMapping();
        this.possibleMoves = createPossibleMoves();
        this.winner = calculateWinner();
        this.isTwoWinners = this.winner.equals("I");
        this.state = validate();
    }

    private List<Coordinate> createPossibleMoves() {
        return coordinateMapping.keySet()
                .stream()
                .filter(coordinates -> cells.charAt(coordinateMapping.get(coordinates)) == ' ')
                .collect(Collectors.toList());
    }

    public static Field fromCells(String cells) {
        String[][] array = new String[3][3];
        for (int i = 0, counter = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++, counter++) {
                array[i][j] = String.valueOf(cells.charAt(counter));
            }
        }

        return new Field(array, cells);
    }

    public Main.State getState() {
        return state;
    }

    public String getWinner() {
        return winner;
    }

    public String getStateName() {
        return this.state.getName();
    }

    public String getPrintableField() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            String[] split = array[i];
            String row = String.format("%s %s %s", split[0], split[1], split[2]);
            result.append(String.format("| %s |\n", row));
        }
        String horizontal = "---------";
        return String.format("%s\n%s\n%s", horizontal, result.toString().trim(), horizontal);
    }

    Main.State validate() {
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

    public Either<String, Field> nextMove(String coordinates, String nextSymbol) {
        char[] chars = cells.toCharArray();
        Either<String, Coordinate> coordinatesEither = Coordinate.fromString(coordinates);
        if (coordinatesEither.isLeft()) {
            return Either.left(coordinatesEither.getLeft());
        }

        Integer orDefault = coordinateMapping.getOrDefault(coordinatesEither.getRight(), -1);

        if (orDefault < 0) {
            return Either.left("Coordinate should be from 1 to 3!");
        } else if (!possibleMoves.contains(coordinatesEither.getRight())) {
            return Either.left("This cell is occupied! Choose another one!");
        } else {
            chars[orDefault] = nextSymbol.charAt(0);
        }

        return Either.right(Field.fromCells(String.valueOf(chars)));
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

    public List<Coordinate> getPossibleMoves() {
        return possibleMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (!Arrays.deepEquals(array, field.array)) return false;
        return state == field.state;
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(array);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
