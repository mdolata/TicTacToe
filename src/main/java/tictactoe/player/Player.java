package tictactoe.player;

import tictactoe.Main;
import tictactoe.util.Either;

public interface Player {
    Either<String, Main.Field> nextMove(Main.Field field);
    String moveMessage();
}
