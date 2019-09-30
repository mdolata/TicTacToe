package tictactoe.player.impl;

import tictactoe.board.Coordinate;
import tictactoe.board.Field;
import tictactoe.player.Player;
import tictactoe.util.Either;

import java.util.List;
import java.util.Random;

public class EasyBotPlayer implements Player {

    private final Random random;
    private final String symbol;

    public EasyBotPlayer(String symbol) {
        this.symbol = symbol;
        random = new Random();
    }

    @Override
    public Either<String, Field> nextMove(Field field) {
        List<Coordinate> possibleMoves = field.getPossibleMoves();
        Coordinate nextCoordinates = possibleMoves.get(random.nextInt(possibleMoves.size()));
        Either<String, Field> nextMove = field.nextMove(nextCoordinates.getCoordinates(), symbol);
        if (nextMove.isRight()) {
            return nextMove;
        }
        return Either.left("Something went wrong with bot player");
    }

    @Override
    public String moveMessage() {
        return "Making move level \"easy\"";
    }
}
