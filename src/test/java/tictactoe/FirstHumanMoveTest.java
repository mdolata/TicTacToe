package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FirstHumanMoveTest {
    private final String input;
    private final String expectedField;
    private final String coordinates;
    private final String expectedResultAfterMove;

    public FirstHumanMoveTest(String input, String expectedField, String coordinates, String expectedResultAfterMove) {
        this.input = input;
        this.expectedField = expectedField;
        this.coordinates = coordinates;
        this.expectedResultAfterMove = expectedResultAfterMove;
    }

    @Parameterized.Parameters
    public static Collection fields() {
        return Arrays.asList(new Object[][]{
                {
                 "X X O    ",
                 "---------\n" +
                 "| X   X |\n" +
                 "|   O   |\n" +
                 "|       |\n" +
                 "---------",
                 "1 1",
                 "---------\n" +
                 "| X   X |\n" +
                 "|   O   |\n" +
                 "| X     |\n" +
                 "---------"
                },
                {
                 " XXOO OX ",
                 "---------\n" +
                 "|   X X |\n" +
                 "| O O   |\n" +
                 "| O X   |\n" +
                 "---------",
                 "1 3",
                 "---------\n" +
                 "| X X X |\n" +
                 "| O O   |\n" +
                 "| O X   |\n" +
                 "---------"
                },
                {
                 " XXOO OX ",
                 "---------\n" +
                 "|   X X |\n" +
                 "| O O   |\n" +
                 "| O X   |\n" +
                 "---------",
                 "3 1",
                 "---------\n" +
                 "|   X X |\n" +
                 "| O O   |\n" +
                 "| O X X |\n" +
                 "---------"
                },
                {
                 " XXOO OX ",
                 "---------\n" +
                 "|   X X |\n" +
                 "| O O   |\n" +
                 "| O X   |\n" +
                 "---------",
                 "3 2",
                 "---------\n" +
                 "|   X X |\n" +
                 "| O O X |\n" +
                 "| O X   |\n" +
                 "---------"
                }
        });

    }

    @Test
    public void generateField() {
        Main.Field field = Main.Field.fromCells(input);
        Assert.assertEquals(expectedField, field.getPrintableField());

        Main.Either<String, Main.Field> nextField = field.nextMove(coordinates, "X");
        Assert.assertTrue(nextField.isRight());
        Assert.assertEquals(expectedResultAfterMove, nextField.getRight().getPrintableField());
    }
}

