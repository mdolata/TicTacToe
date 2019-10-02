package tictactoe.game;

import tictactoe.board.Field;
import tictactoe.board.State;
import tictactoe.player.Player;
import tictactoe.util.Either;

public class GameLoop {
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

            System.out.print(currentPlayer.moveMessage());
            Either<String, Field> nextField = currentPlayer.nextMove(field);
            if (nextField.isRight()) {
                moveCount++;
                field = nextField.getRight();
                System.out.println(field.getPrintableField());
            } else {
                System.out.println(nextField.getLeft());
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

