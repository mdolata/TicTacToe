package tictactoe.game;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tictactoe.util.Either;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CommandValidatorTest {

    private final CommandValidator commandValidator = createCommandValidator();
    private final String command;
    private final Either<String, String[]> expectedResult;

    public CommandValidatorTest(String command, Either<String, String[]> expectedResult) {
        this.command = command;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection fields(){
        return Arrays.asList(new Object[][]{
                {"start", badParameters()},
                {"start easy", badParameters()},
                {"start easy medium", badParameters()},
                {"not existing commands", badParameters()},
                {"start exit", badParameters()},
                {"exit user", badParameters()},
                {"start bad bad", badParameters()},
                {"bad easy easy", badParameters()},
                {"start easy bad", badParameters()},
                {"nope", badParameters()},
                {"start user easy", Either.right(new String[]{"start", "user", "easy"})},
                {"start easy user", Either.right(new String[]{"start", "easy", "user"})},
                {"start easy easy", Either.right(new String[]{"start", "easy", "easy"})},
                {"exit", Either.right(new String[]{"exit"})},
        });
    }

    @Test
    public void shouldPassCorrectCommands() {
        Either<String, String[]> actual = commandValidator.validate(command);

        if (actual.isRight()) {
            Assert.assertTrue(actual.isRight());
            Assert.assertArrayEquals(expectedResult.getRight(), actual.getRight());
        } else {
            Assert.assertTrue(actual.isLeft());
            Assert.assertEquals(expectedResult.getLeft(), actual.getLeft());
        }
    }


    private static Either<String, Object> badParameters() {
        return Either.left("Bad parameters!");
    }

    private CommandValidator createCommandValidator() {
        List<String> supportedFunctionalCommands = new ArrayList<>();
        supportedFunctionalCommands.add("start");
        supportedFunctionalCommands.add("exit");
        List<String> supportedLevelCommands = new ArrayList<>();
        supportedLevelCommands.add("user");
        supportedLevelCommands.add("easy");

        return new CommandValidator(supportedLevelCommands, supportedFunctionalCommands);
    }


}
