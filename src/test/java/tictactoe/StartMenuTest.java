package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import tictactoe.game.CommandValidator;
import tictactoe.game.StartMenu;

import java.util.ArrayList;
import java.util.List;

public class StartMenuTest {

    @Test
    public void shouldSimulateGame() {
        StartMenu startMenu = new StartMenu(null, createCommandValidator());
        Assert.assertEquals("initial", startMenu.getLastState());
        Assert.assertTrue(startMenu.isRunning());

        startMenu.validateAndRun("start");
        Assert.assertEquals("Bad parameters!", startMenu.getLastState());
        Assert.assertTrue(startMenu.isRunning());

        startMenu.validateAndRun("start easy easy");
        Assert.assertEquals("game ended", startMenu.getLastState());
        Assert.assertTrue(startMenu.isRunning());

        startMenu.validateAndRun("start easy medium");
        Assert.assertEquals("game ended", startMenu.getLastState());
        Assert.assertTrue(startMenu.isRunning());

        startMenu.validateAndRun("exit");
        Assert.assertEquals("exiting", startMenu.getLastState());
        Assert.assertFalse(startMenu.isRunning());

        startMenu.validateAndRun("start easy easy");
        Assert.assertEquals("game ended", startMenu.getLastState());
        Assert.assertFalse(startMenu.isRunning());

    }

    //todo same in CommandValidatorTest. move it to utils
    private CommandValidator createCommandValidator() {
        List<String> supportedFunctionalCommands = new ArrayList<>();
        supportedFunctionalCommands.add("start");
        supportedFunctionalCommands.add("exit");
        List<String> supportedLevelCommands = new ArrayList<>();
        supportedLevelCommands.add("user");
        supportedLevelCommands.add("easy");
        supportedLevelCommands.add("medium");

        return new CommandValidator(supportedLevelCommands, supportedFunctionalCommands);
    }


}
