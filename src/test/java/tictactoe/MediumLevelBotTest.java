package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MediumLevelBotTest {
    private final String input;
    private final String expected;
    private final String description;

    public MediumLevelBotTest(String input, String expected, String description) {
        this.input = input;
        this.expected = expected;
        this.description = description;
    }

    @Parameterized.Parameters
    public static Collection fields() {
        return Arrays.asList(new Object[][]{
                {"X X" +
                 "  O" +
                 "O  ",
                 "XXX" +
                 "  O" +
                 "O  ", "Bot can win in one move" },
                {"  X" +
                 "O  " +
                 "OX ",
                 "X X" +
                 "O  " +
                 "OX ", "Opponent can win in one move" }
        });

    }

    @Test
    public void mediumBodShouldMoveWithLogic() {
        Main.Field inputField = Main.Field.fromCells(input);
        Main.Field expectedField = Main.Field.fromCells(expected);

        Main.Player botPlayer = new Main.EasyBotPlayer("X");
        Main.Either<String, Main.Field> nextField = botPlayer.nextMove(inputField);

        Assert.assertTrue(nextField.isRight());
        Assert.assertEquals(description, expectedField, nextField.getRight());
    }

    @Test
    public void mediumBodShouldMoveWithRandom() {
        Main.Field inputField = Main.Field.fromCells("  X  OOX ");

        Main.Player botPlayer = new Main.MediumBotPlayer("X");
        Main.Either<String, Main.Field> nextField = botPlayer.nextMove(inputField);

        Assert.assertTrue(nextField.isRight());
        Assert.assertNotEquals("No one can win in one move, random!", inputField, nextField.getRight());
    }
}

