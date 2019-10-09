package tictactoe.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tictactoe.board.State;
import tictactoe.player.Player;
import tictactoe.player.PlayerFactory;
import tictactoe.util.Either;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class StartMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartMenu.class);
    private final Scanner scanner;
    private final CommandValidator commandValidator;
    private final AtomicBoolean isRunning;
    private String lastState;

    public StartMenu(Scanner scanner, CommandValidator commandValidator) {
        this.scanner = scanner;
        this.commandValidator = commandValidator;
        isRunning = new AtomicBoolean(true);
        lastState = "initial";
    }

    public void start() {
        while (isRunning.get()) {
            LOGGER.info("Input command: ");
            String command = scanner.nextLine();

            validateAndRun(command);
        }
    }

    public void validateAndRun(String command) {
        Either<String, String[]> validation = commandValidator.validate(command);

        if (validation.isLeft()) {
            LOGGER.info(validation.getLeft());
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
            LOGGER.info(run.getName());
            lastState = "game ended";
        }
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public String getLastState() {
        return lastState;
    }

}
