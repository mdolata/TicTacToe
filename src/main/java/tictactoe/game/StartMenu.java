package tictactoe.game;

import tictactoe.board.State;
import tictactoe.player.Player;
import tictactoe.player.PlayerFactory;
import tictactoe.util.Either;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class StartMenu {
    private final Scanner scanner;
    private final CommandValidator commandValidator;
    private AtomicBoolean isRunning;
    private String lastState;

    public StartMenu(Scanner scanner, CommandValidator commandValidator) {
        this.scanner = scanner;
        this.commandValidator = commandValidator;
        isRunning = new AtomicBoolean(true);
        lastState = "initial";
    }

    public void start() {
        while (isRunning.get()) {
            System.out.println("Input command: ");
            String command = scanner.nextLine();

            validateAndRun(command);
        }
    }

    public String validateAndRun(String command) {
        Either<String, String[]> validation = commandValidator.validate(command);

        if (validation.isLeft()) {
            System.out.println(validation.getLeft());
            lastState = validation.getLeft();
        } else if ("exit".equals(validation.getRight()[0])) {
            isRunning.set(false);
            lastState = "exiting";
        } else if ("start".equals(validation.getRight()[0])) {
            String[] commands = validation.getRight();
            Player player1 = PlayerFactory.create(commands[1], "X");
            Player player2 = PlayerFactory.create(commands[2], "O");

            GameLoop gameLoop = new GameLoop(new Player[]{player1, player2});
            State run = gameLoop.run();
            System.out.println(run);
            lastState = "game ended";
        }
        return lastState;
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public String getLastState() {
        return lastState;
    }

}
