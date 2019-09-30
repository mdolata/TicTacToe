package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import tictactoe.board.Field;
import tictactoe.board.State;
import tictactoe.game.GameLoop;
import tictactoe.player.Player;
import tictactoe.player.impl.EasyBotPlayer;

public class GameLoopTest {

    @Test
    public void gameFlowTest() {
        for (int i = 0; i < 1000; i++) {

            Player firstBotPlayer = new EasyBotPlayer("X");
            Player secondBotPlayer = new EasyBotPlayer("O");
            GameLoop gameLoop = new GameLoop(new Player[]{firstBotPlayer, secondBotPlayer});
            State state = gameLoop.run();

            Assert.assertTrue("Minimum moves -> 5", gameLoop.getMoveCount() >= 5);
            Assert.assertTrue("Maximum moves -> 9", gameLoop.getMoveCount() <= 9);
            Assert.assertTrue("Final state should be terminal state", state.isTerminal());
            Assert.assertTrue("Draw state should end with 9 moves", isCondition(gameLoop.getField(), gameLoop.getMoveCount()));
        }
    }

    private boolean isCondition(Field field, int movesCounter) {
        if (field.getStateName().equals(State.DRAW.getName()))
            return movesCounter == 9;
        return true;
    }
}
