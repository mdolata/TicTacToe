package tictactoe.player;

import tictactoe.board.Field;
import tictactoe.util.Either;

public interface Player {
    Either<String, Field> nextMove(Field field);
    String moveMessage();
}
