package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tictactoe.player.Player;
import tictactoe.player.impl.EasyBotPlayer;

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
        Main.Field inputField = Main.Field.fromCells(input);
        Player botPlayer = this.botPlayer;
        Main.Either<String, Main.Field> nextField = botPlayer.nextMove(inputField);

        Assert.assertTrue(nextField.isRight());
        Assert.assertNotEquals(inputField, nextField.getRight());
    }
}
