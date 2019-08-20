package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FieldValidationTest {
    private final String input;
    private final String expectedResult;

    public FieldValidationTest(String input, String expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection fields(){
        return Arrays.asList(new Object[][]{
                {"XXXOO  O ",   "X wins"},
                {"XO X OX  ",   "X wins"},
                {"XOXOXOXXO",   "X wins"},
                {"XOXOXOOXX",   "X wins"},
                {"XOOOXOXXO",   "O wins"},
                {"OOOXXOXXO",   "O wins"},
                {"XOXOOXXXO",   "Draw"},
                {"XO OOX X ",   "Game not finished"},
                {"XO " +
                 "XO " +
                 "XOX",   "Impossible"},
                {" O X  X X",   "Impossible"},
                {" OOOO X X",   "Impossible"},
                {"XXXXXXXXX",   "Impossible"},
        });

    }

    @Test
    public void generateField() {
        String field = Main.FieldValidator.validate(Main.Field.fromCells(input)).getName();
        Assert.assertEquals(expectedResult, field);
    }
}
