package tictactoe.board;

import org.junit.Assert;
import org.junit.Test;
import tictactoe.util.Either;

import java.util.ArrayList;
import java.util.List;

public class CoordinateTest {

    @Test
    public void shouldCreateCoordinatesFromValidString() {
        Either<String, Coordinate> coordinateEither = Coordinate.fromString("1 1");

        Assert.assertTrue(coordinateEither.isRight());
    }

    @Test
    public void shouldReturnErrorWithMessageForInvalidString() {
        String expectMessage = "You should two enter numbers with one space!";
        List<String> invalidStrings = getInvalidStrings();

        for (String invalidString : invalidStrings) {
            Either<String, Coordinate> coordinateEither = Coordinate.fromString(invalidString);

            Assert.assertTrue(coordinateEither.isLeft());
            Assert.assertEquals(expectMessage, coordinateEither.getLeft());
        }
    }

    private List<String> getInvalidStrings() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a 1");
        strings.add("1 a");
        strings.add("a a");
        strings.add("a");
        strings.add("1 ");
        strings.add("1  1");
        return strings;
    }
}
