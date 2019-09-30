package tictactoe.player;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tictactoe.board.Field;
import tictactoe.player.Player;
import tictactoe.player.impl.EasyBotPlayer;
import tictactoe.util.Either;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EasyLevelMoveTest {
    private final String input;
    private final Player botPlayer;

    public EasyLevelMoveTest(String input,
                             Player botPlayer) {
        this.input = input;
        this.botPlayer = botPlayer;
    }

    @Parameterized.Parameters
    public static Collection fields() {
        return Arrays.asList(new Object[][]{
                {"  XO  OX ", new EasyBotPlayer("X")},
                {"  XO  OX ", new EasyBotPlayer("X")},
                {"  XO  OX ", new EasyBotPlayer("X")}
        });

    }

    @Test
    public void botPlayerMove() {
        Field inputField = Field.fromCells(input);
        Player botPlayer = this.botPlayer;
        Either<String, Field> nextField = botPlayer.nextMove(inputField);

        Assert.assertTrue(nextField.isRight());
        Assert.assertNotEquals(inputField, nextField.getRight());
    }
}
