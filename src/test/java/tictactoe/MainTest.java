package tictactoe;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MainTest {

    private final String input;
    private final String expectedResult;

    public MainTest(String input, String expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection fields(){
        return Arrays.asList(new Object[][]{
                {"O OXXO XX",   "---------\n" +
                                "| O   O |\n" +
                                "| X X O |\n" +
                                "|   X X |\n" +
                                "---------"},
                {"OXO  X OX",   "---------\n" +
                                "| O X O |\n" +
                                "|     X |\n" +
                                "|   O X |\n" +
                                "---------"},
                {" XO  X   ",   "---------\n" +
                                "|   X O |\n" +
                                "|     X |\n" +
                                "|       |\n" +
                                "---------"},
        });

    }

    @Test
    public void generateField() {
        String field = Main.generateField(input);
        Assert.assertEquals(expectedResult, field);
    }
}