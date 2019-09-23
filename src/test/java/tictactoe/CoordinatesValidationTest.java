package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tictactoe.board.Field;
import tictactoe.util.Either;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CoordinatesValidationTest {
    private final String input;
    private final String coordinates;
    private final String expectedResult;

    public CoordinatesValidationTest(String input, String coordinates, String expectedResult) {
        this.input = input;
        this.coordinates = coordinates;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection fields(){
        return Arrays.asList(new Object[][]{
                {" XXOO OX ", "1 1", "This cell is occupied! Choose another one!"},
                {" XXOO OX ", "one", "You should enter numbers!"},
                {" XXOO OX ", "one three", "You should enter numbers!"},
                {" XXOO OX ", "4 1", "Coordinate should be from 1 to 3!"},
                {" XXOO OX ", "1 4", "Coordinate should be from 1 to 3!"}
        });

    }

    @Test
    public void generateField() {
        Field inputField = Field.fromCells(input);
        Either<String, Field> nextField = inputField.nextMove(coordinates, "X");

        Assert.assertTrue(nextField.isLeft());
        Assert.assertEquals(expectedResult, nextField.getLeft());
    }
}
