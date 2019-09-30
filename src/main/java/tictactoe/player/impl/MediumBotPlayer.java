package tictactoe.player.impl;

import tictactoe.board.Coordinate;
import tictactoe.board.Field;
import tictactoe.player.Player;
import tictactoe.util.Either;

import java.util.List;
import java.util.Random;

public class MediumBotPlayer implements Player {

    private final Random random;
    private final String symbol;

    public MediumBotPlayer(String symbol) {
        this.symbol = symbol;
        random = new Random();
    }

    @Override
    public Either<String, Field> nextMove(Field field) {
        List<Coordinate> possibleMoves = field.getPossibleMoves();
        Either<String, Field> nextMove1 = canIWinInNextMove(field);
        if (nextMove1.isRight()) return nextMove1;

        Either<String, Field> nextMove2 = canOpponentWinInNextMove(field);
        if (nextMove2.isRight()) return nextMove2;

        Coordinate nextCoordinates = possibleMoves.get(random.nextInt(possibleMoves.size()));
        Either<String, Field> nextMove = field.nextMove(nextCoordinates.getCoordinates(), symbol);
        if (nextMove.isRight()) {
            return nextMove;
        }
        return Either.left("Something went wrong with bot player");
    }

    private Either<String, Field> canOpponentWinInNextMove(Field field) {
        List<Coordinate> possibleMoves = field.getPossibleMoves();
        for (Coordinate possibleMove : possibleMoves) {
            String opponentSymbol = otherSymbol();
            Either<String, Field> nextMove = field.nextMove(possibleMove.getCoordinates(), opponentSymbol);
            if (nextMove.getRight().getWinner().equals(opponentSymbol)) {
                return field.nextMove(possibleMove.getCoordinates(), symbol);
            }
        }
        return Either.left("");
    }

    private Either<String, Field> canIWinInNextMove(Field field) {
        List<Coordinate> possibleMoves = field.getPossibleMoves();
        for (Coordinate possibleMove : possibleMoves) {
            Either<String, Field> nextMove = field.nextMove(possibleMove.getCoordinates(), symbol);
            if (nextMove.getRight().getWinner().equals(symbol)) {
                return nextMove;
            }
        }
        return Either.left("");
    }

    //TODO coupling on symbol value
    private String otherSymbol(){
        if (symbol.equals("X")) return "O";
        else return "X";
    }

    @Override
    public String moveMessage() {
        return "Making move level \"medium\"";
    }
}
