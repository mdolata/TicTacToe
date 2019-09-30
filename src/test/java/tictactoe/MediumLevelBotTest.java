package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tictactoe.board.Field;
import tictactoe.player.Player;
import tictactoe.player.impl.MediumBotPlayer;
import tictactoe.util.Either;

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
    public void mediumBotShouldMoveWithLogic() {
        Field inputField = Field.fromCells(input);
        Field expectedField = Field.fromCells(expected);

        Player botPlayer = new MediumBotPlayer("X");
        Either<String, Field> nextField = botPlayer.nextMove(inputField);

        Assert.assertTrue(nextField.isRight());
        Assert.assertEquals(description, expectedField, nextField.getRight());
    }

    @Test
    public void mediumBotShouldMoveWithRandom() {
        Field inputField = Field.fromCells("  X  OOX ");

        Player botPlayer = new MediumBotPlayer("X");
        Either<String, Field> nextField = botPlayer.nextMove(inputField);

        Assert.assertTrue(nextField.isRight());
        Assert.assertNotEquals("No one can win in one move, random!", inputField, nextField.getRight());
    }
}

