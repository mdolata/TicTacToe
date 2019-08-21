package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EasyLevelMoveTest {
    private final String input;
    private final Main.BotPlayer botPlayer;

    public EasyLevelMoveTest(String input,
                             Main.BotPlayer botPlayer) {
        this.input = input;
        this.botPlayer = botPlayer;
    }

    @Parameterized.Parameters
    public static Collection fields() {
        return Arrays.asList(new Object[][]{
                {"  XO  OX ", new Main.EasyBotPlayer()},
                {"  XO  OX ", new Main.EasyBotPlayer()},
                {"  XO  OX ", new Main.EasyBotPlayer()}
        });

    }

    @Test
    public void botPlayerMove() {
        Main.Field inputField = Main.Field.fromCells(input);
        Main.BotPlayer botPlayer = this.botPlayer;
        Main.Either<String, Main.Field> nextField = botPlayer.nextMove(inputField);

        Assert.assertTrue(nextField.isRight());
        Assert.assertNotEquals(inputField, nextField.getRight());
    }
}
