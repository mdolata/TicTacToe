package tictactoe.board;

import tictactoe.util.Either;

public class Coordinate {

    private final String coordinates;
    private final Integer x;
    private final Integer y;

    private Coordinate(String coordinates, int x, int y) {
        this.coordinates = coordinates;
        this.x = x;
        this.y = y;
    }

    public static Either<String, Coordinate> fromString(String coordinates) {
        String[] split = coordinates.split(" ");
        int x, y;
        try {
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return Either.left("You should two enter numbers with one space!");
        }
        return Either.right(new Coordinate(coordinates, x, y));
    }

    public String getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return String.format("(%s)", coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (coordinates != null ? !coordinates.equals(that.coordinates) : that.coordinates != null) return false;
        if (!x.equals(that.x)) return false;
        return y.equals(that.y);
    }

    @Override
    public int hashCode() {
        int result = coordinates != null ? coordinates.hashCode() : 0;
        result = 31 * result + x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}
