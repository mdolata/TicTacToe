package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class CommandValidatorTest {

    private Main.CommandValidator commandValidator = createCommandValidator();
    private final String command;
    private final Main.Either<String, String[]> expectedResult;

    public CommandValidatorTest(String command, Main.Either<String, String[]> expectedResult) {
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
                {"nope", badParameters()},
                {"start user easy", Main.Either.right(new String[]{"start", "user", "easy"})},
                {"start easy user", Main.Either.right(new String[]{"start", "easy", "user"})},
                {"start easy easy", Main.Either.right(new String[]{"start", "easy", "easy"})},
                {"exit", Main.Either.right(new String[]{"exit"})},
        });

    }

    @Test
    public void shouldPassCorrectCommands() {
        Main.Either<String, String[]> actual = commandValidator.validate(command);

        if (actual.isRight()) {
            Assert.assertTrue(actual.isRight());
            Assert.assertArrayEquals(expectedResult.getRight(), actual.getRight());
        } else {
            Assert.assertTrue(actual.isLeft());
            Assert.assertEquals(expectedResult.getLeft(), actual.getLeft());
        }
    }


    private static Main.Either<String, Object> badParameters() {
        return Main.Either.left("Bad parameters!");
    }

    private Main.CommandValidator createCommandValidator() {
        List<String> supportedFunctionalCommands = new ArrayList<>();
        supportedFunctionalCommands.add("start");
        supportedFunctionalCommands.add("exit");
        List<String> supportedLevelCommands = new ArrayList<>();
        supportedLevelCommands.add("user");
        supportedLevelCommands.add("easy");

        return new Main.CommandValidator(supportedLevelCommands, supportedFunctionalCommands);
    }


}
