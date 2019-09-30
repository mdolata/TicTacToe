package tictactoe.game;

import tictactoe.util.Either;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandValidator {
    private final List<String> supportedLevelCommands;
    private final List<String> supportedFunctionalCommands;

    public CommandValidator(List<String> supportedLevelCommands, List<String> supportedFunctionalCommands) {
        this.supportedLevelCommands = supportedLevelCommands;
        this.supportedFunctionalCommands = supportedFunctionalCommands;
    }


    public Either<String, String[]> validate(String command) {
        String[] commands = Arrays.stream(command.split(" "))
                .filter(this::isSupport)
                .toArray(String[]::new);

        if (commands.length == 1 && "exit".equals(commands[0])) {
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
