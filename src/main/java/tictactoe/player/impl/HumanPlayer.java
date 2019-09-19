package tictactoe.player.impl;

import tictactoe.Main;
import tictactoe.player.Player;

import java.util.Scanner;

public class HumanPlayer implements Player {

    private final String symbol;
    private final Scanner scanner;

    public HumanPlayer(String symbol) {
        this.symbol = symbol;
        scanner = new Scanner(System.in);
    }

    @Override
    public Main.Either<String, Main.Field> nextMove(Main.Field field) {
        String nextCoordinates = scanner.nextLine();
        return field.nextMove(nextCoordinates, symbol);
    }

    @Override
    public String moveMessage() {
        return "Enter the coordinates: ";
    }
}
