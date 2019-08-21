package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import tictactoe.Main.State;

import java.util.ArrayList;
import java.util.List;

public class GameFlowTest {

    final List<String> terminalStates;

    public GameFlowTest() {
        this.terminalStates = new ArrayList<>();
        terminalStates.add(State.DRAW.getName());
        terminalStates.add(State.O_WINS.getName());
        terminalStates.add(State.X_WINS.getName());
    }

    @Test
    public void gameFlowTest() {
        for (int i = 0; i < 1000; i++) {

            Main.Field field = Main.Field.fromCells("         ");
            Main.BotPlayer firstBotPlayer = new Main.EasyBotPlayer("X");
            Main.BotPlayer secondBotPlayer = new Main.EasyBotPlayer("O");
            int movesCounter = 0;
            boolean firstPlayerTurn = false;
            System.out.println(field.getPrintableField());
            while (field.getStateName().equals(State.GAME_NOT_FINISHED.getName())) {
                movesCounter++;
                Main.BotPlayer currentPlayer;

                if (firstPlayerTurn) {
                    currentPlayer = firstBotPlayer;
                } else {
                    currentPlayer = secondBotPlayer;
                }

                System.out.println(currentPlayer.moveMessage());
                Main.Either<String, Main.Field> nextMove = currentPlayer.nextMove(field);

                if (nextMove.isLeft()) {
                    throw new RuntimeException("Something went wrong");
                }

                field = nextMove.getRight();

                System.out.println(field.getPrintableField());
                firstPlayerTurn = !firstPlayerTurn;
            }

            System.out.println(field.getStateName());
            Assert.assertTrue("Minimum moves -> 5", movesCounter >= 5);
            Assert.assertTrue("Maximum moves -> 9", movesCounter <= 9);
            Assert.assertTrue("Final state should be terminal state", terminalStates.contains(field.getStateName()));
            Assert.assertTrue("Draw state should end with 9 moves", isCondition(field, movesCounter));
        }
    }

    private boolean isCondition(Main.Field field, int movesCounter) {
        if (field.getStateName().equals(State.DRAW.getName()))
            return movesCounter == 9;
        return true;
    }
}
