package tictactoe.player.impl;

import tictactoe.Main;
import tictactoe.player.Player;
import tictactoe.player.PlayerFactory;
import tictactoe.util.Either;

public class HardBotPlayer implements Player {

    // todo implement minimax algorithm
    private final Player delegate;

    public HardBotPlayer(String symbol) {
        delegate = PlayerFactory.create("medium", symbol);
    }
    @Override
    public Either<String, Main.Field> nextMove(Main.Field field) {
        return delegate.nextMove(field);
    }

    @Override
    public String moveMessage() {
        return "Making move level \"hard\"";
    }
}
