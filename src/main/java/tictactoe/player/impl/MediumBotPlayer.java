package tictactoe.player.impl;

import tictactoe.Main;
import tictactoe.player.Player;

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
    public Main.Either<String, Main.Field> nextMove(Main.Field field) {
        List<Main.Coordinates> possibleMoves = field.getPossibleMoves();
        Main.Either<String, Main.Field> nextMove1 = canIWinInNextMove(field);
        if (nextMove1.isRight()) return nextMove1;

        Main.Either<String, Main.Field> nextMove2 = canOpponentWinInNextMove(field);
        if (nextMove2.isRight()) return nextMove2;

        Main.Coordinates nextCoordinates = possibleMoves.get(random.nextInt(possibleMoves.size()));
        Main.Either<String, Main.Field> nextMove = field.nextMove(nextCoordinates.getCoordinates(), symbol);
        if (nextMove.isRight()) {
            return nextMove;
        }
        return Main.Either.left("Something went wrong with bot player");
    }

    private Main.Either<String, Main.Field> canOpponentWinInNextMove(Main.Field field) {
        List<Main.Coordinates> possibleMoves = field.getPossibleMoves();
        for (Main.Coordinates possibleMove : possibleMoves) {
            String opponentSymbol = otherSymbol();
            Main.Either<String, Main.Field> nextMove = field.nextMove(possibleMove.getCoordinates(), opponentSymbol);
            if (nextMove.getRight().getWinner().equals(opponentSymbol)) {
                return field.nextMove(possibleMove.getCoordinates(), symbol);
            }
        }
        return Main.Either.left("");
    }

    private Main.Either<String, Main.Field> canIWinInNextMove(Main.Field field) {
        List<Main.Coordinates> possibleMoves = field.getPossibleMoves();
        for (Main.Coordinates possibleMove : possibleMoves) {
            Main.Either<String, Main.Field> nextMove = field.nextMove(possibleMove.getCoordinates(), symbol);
            if (nextMove.getRight().getWinner().equals(symbol)) {
                return nextMove;
            }
        }
        return Main.Either.left("");
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
