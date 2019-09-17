package tictactoe;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StartMenuTest {

//    @Test
    public void shouldSimulateGame() {
        Main.StartMenu startMenu = new Main.StartMenu(null, createCommandValidator());
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

    private Main.CommandValidator createCommandValidator() {
        List<String> supportedFunctionalCommands = new ArrayList<>();
        supportedFunctionalCommands.add("start");
        supportedFunctionalCommands.add("exit");
        List<String> supportedLevelCommands = new ArrayList<>();
        supportedLevelCommands.add("user");
        supportedLevelCommands.add("easy");
        supportedLevelCommands.add("medium");

        return new Main.CommandValidator(supportedLevelCommands, supportedFunctionalCommands);
    }


}
