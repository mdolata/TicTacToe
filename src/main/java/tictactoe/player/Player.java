package tictactoe.player;

import tictactoe.Main;

public interface Player {
    Main.Either<String, Main.Field> nextMove(Main.Field field);
    String moveMessage();
}
