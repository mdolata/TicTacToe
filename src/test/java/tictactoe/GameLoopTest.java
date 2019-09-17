package tictactoe;

import org.junit.Assert;
import org.junit.Test;

public class GameLoopTest {

    @Test
    public void gameFlowTest() {
        for (int i = 0; i < 1000; i++) {

            Main.Player firstBotPlayer = new Main.EasyBotPlayer("X");
            Main.Player secondBotPlayer = new Main.EasyBotPlayer("O");
            Main.GameLoop gameLoop = new Main.GameLoop(new Main.Player[]{firstBotPlayer, secondBotPlayer});
            Main.State state = gameLoop.run();

            Assert.assertTrue("Minimum moves -> 5", gameLoop.getMoveCount() >= 5);
            Assert.assertTrue("Maximum moves -> 9", gameLoop.getMoveCount() <= 9);
            Assert.assertTrue("Final state should be terminal state", state.isTerminal());
            Assert.assertTrue("Draw state should end with 9 moves", isCondition(gameLoop.getField(), gameLoop.getMoveCount()));
        }
    }

    private boolean isCondition(Main.Field field, int movesCounter) {
        if (field.getStateName().equals(Main.State.DRAW.getName()))
            return movesCounter == 9;
        return true;
    }
}
