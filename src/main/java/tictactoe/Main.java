package tictactoe;

import tictactoe.game.CommandValidator;
import tictactoe.game.StartMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        List<String> supportedFunctionalCommands = getSupportedFunctionalCommands();
        List<String> supportedLevelCommands = getSupportedLevelCommands();

        CommandValidator commandValidator = new CommandValidator(supportedLevelCommands, supportedFunctionalCommands);

        StartMenu startMenu = new StartMenu(new Scanner(System.in), commandValidator);
        startMenu.start();

    }

    //todo read from properties file
    private static List<String> getSupportedLevelCommands() {
        List<String> supportedLevelCommands = new ArrayList<>();
        supportedLevelCommands.add("user");
        supportedLevelCommands.add("easy");
        supportedLevelCommands.add("medium");
        supportedLevelCommands.add("hard");
        return supportedLevelCommands;
    }

    //todo read from properties file
    private static List<String> getSupportedFunctionalCommands() {
        List<String> supportedFunctionalCommands = new ArrayList<>();
        supportedFunctionalCommands.add("start");
        supportedFunctionalCommands.add("exit");
        return supportedFunctionalCommands;
    }

}
