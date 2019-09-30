package tictactoe;

import tictactoe.board.Field;
import tictactoe.board.State;
import tictactoe.player.Player;
import tictactoe.player.PlayerFactory;
import tictactoe.util.Either;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        List<String> supportedFunctionalCommands = new ArrayList<>();
        supportedFunctionalCommands.add("start");
        supportedFunctionalCommands.add("exit");
        List<String> supportedLevelCommands = new ArrayList<>();
        supportedLevelCommands.add("user");
        supportedLevelCommands.add("easy");
        supportedLevelCommands.add("medium");
        supportedLevelCommands.add("hard");

        CommandValidator commandValidator = new CommandValidator(supportedLevelCommands, supportedFunctionalCommands);


        StartMenu startMenu = new StartMenu(new Scanner(System.in), commandValidator);
        startMenu.start();

    }

    static class StartMenu {
        private AtomicBoolean isRunning;
        private final Scanner scanner;
        private final CommandValidator commandValidator;
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

        public boolean isRunning(){
            return isRunning.get();
        }

        public String getLastState() {
            return lastState;
        }
    }

    static class CommandValidator {
        private final List<String> supportedLevelCommands;
        private final List<String> supportedFunctionalCommands;

        CommandValidator(List<String> supportedLevelCommands, List<String> supportedFunctionalCommands) {
            this.supportedLevelCommands = supportedLevelCommands;
            this.supportedFunctionalCommands = supportedFunctionalCommands;
        }


        public Either<String, String[]> validate(String command) {
            String[] commands = Arrays.stream(command.split(" "))
                    .filter(this::isSupport)
                    .toArray(String[]::new);

            if (commands.length == 1 && "exit".equals(commands[0])){
                return Either.right(commands);
            }

            if (commands.length != 3) {
                return Either.left("Bad parameters!");
            }

            if (supportedFunctionalCommands.contains(commands[0]) &&
                    supportedLevelCommands.contains(commands[1]) &&
                    supportedLevelCommands.contains(commands[2])) {
                return Either.right(commands);
            }

            return Either.left("Bad parameters!");
        }

        private boolean isSupport(String command) {
            return !Objects.nonNull(command) ||
                    supportedLevelCommands.contains(command) ||
                    supportedFunctionalCommands.contains(command);
        }
    }

    static class GameLoop {
        private final Player[] players;
        private int moveCount;
        private Field field;

        GameLoop(Player[] players) {
            this.players = players;
            moveCount = 0;
            field = Field.fromCells("         ");
        }

        State run () {
            Player currentPlayer;
            do {
                if (moveCount % 2 == 0) {
                    currentPlayer = players[0];
                } else {
                    currentPlayer = players[1];
                }

                System.out.println(currentPlayer.moveMessage());
                Either<String, Field> nextField = currentPlayer.nextMove(field);
                if (nextField.isRight()) {
                    moveCount++;
                    field = nextField.getRight();
                    System.out.println(field.getPrintableField());
                } else {
                    System.out.println(nextField.getLeft());
                }
            } while (! field.getState().isTerminal());

            return field.getState();
        }

        int getMoveCount() {
            return moveCount;
        }

        Field getField() {
            return field;
        }
    }

}
