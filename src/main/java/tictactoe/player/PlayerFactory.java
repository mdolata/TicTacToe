package tictactoe.player;

import tictactoe.player.impl.EasyBotPlayer;
import tictactoe.player.impl.HardBotPlayer;
import tictactoe.player.impl.HumanPlayer;
import tictactoe.player.impl.MediumBotPlayer;

public class PlayerFactory {
    public static Player create(String playerType, String symbol) {
        switch (playerType) {
            case "user": return new HumanPlayer(symbol);
            case "easy": return new EasyBotPlayer(symbol);
            case "medium": return new MediumBotPlayer(symbol);
            case "hard": return new HardBotPlayer(symbol);
            //TODO remove exception
            default: throw new RuntimeException();
        }
    }
}
