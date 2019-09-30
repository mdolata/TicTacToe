package tictactoe.game;

import org.junit.Assert;
import org.junit.Test;
import tictactoe.board.Field;
import tictactoe.board.State;
import tictactoe.player.Player;
import tictactoe.player.impl.EasyBotPlayer;
import tictactoe.util.Either;

public class GameFlowTest {

    @Test
    public void gameFlowTest() {
        for (int i = 0; i < 1000; i++) {

            Field field = Field.fromCells("         ");
            Player firstBotPlayer = new EasyBotPlayer("X");
            Player secondBotPlayer = new EasyBotPlayer("O");
            int movesCounter = 0;
            boolean firstPlayerTurn = false;
            while (field.getStateName().equals(tictactoe.board.State.GAME_NOT_FINISHED.getName())) {
                movesCounter++;
                Player currentPlayer;

                if (firstPlayerTurn) {
                    currentPlayer = firstBotPlayer;
                } else {
                    currentPlayer = secondBotPlayer;
                }

                Either<String, Field> nextMove = currentPlayer.nextMove(field);

                if (nextMove.isLeft()) {
                    throw new RuntimeException("Something went wrong");
                }

                field = nextMove.getRight();

                firstPlayerTurn = !firstPlayerTurn;
            }

            Assert.assertTrue("Minimum moves -> 5", movesCounter >= 5);
            Assert.assertTrue("Maximum moves -> 9", movesCounter <= 9);
            Assert.assertTrue("Final state should be terminal state", field.getState().isTerminal());
            Assert.assertTrue("Draw state should end with 9 moves", isCondition(field, movesCounter));
        }
    }

    private boolean isCondition(Field field, int movesCounter) {
        if (field.getStateName().equals(State.DRAW.getName()))
            return movesCounter == 9;
        return true;
    }
}
