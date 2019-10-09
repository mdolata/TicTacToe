package tictactoe.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tictactoe.board.Field;
import tictactoe.board.State;
import tictactoe.player.Player;
import tictactoe.util.Either;

public class GameLoop {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoop.class);
    private final Player[] players;
    private int moveCount;
    private Field field;

    GameLoop(Player[] players) {
        this.players = players;
        moveCount = 0;
        field = Field.fromCells("         ");
    }

    State run() {
        Player currentPlayer;
        do {
            if (moveCount % 2 == 0) {
                currentPlayer = players[0];
            } else {
                currentPlayer = players[1];
            }

            LOGGER.info(currentPlayer.moveMessage());
            Either<String, Field> nextField = currentPlayer.nextMove(field);
            if (nextField.isRight()) {
                moveCount++;
                field = nextField.getRight();
                LOGGER.info(field.getPrintableField());
            } else {
                LOGGER.info(nextField.getLeft());
            }
        } while (!field.getState().isTerminal());

        return field.getState();
    }

    public int getMoveCount() {
        return moveCount;
    }

    public Field getField() {
        return field;
    }
}

