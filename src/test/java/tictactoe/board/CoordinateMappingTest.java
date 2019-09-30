package tictactoe.board;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class CoordinateMappingTest {

    @Test
    public void getPrintableMapping() {
        String actualPrintableMapping = CoordinateMapping.getPrintableMapping();

        String expected = "(1 3) (2 3) (3 3)" +
                "(1 2) (2 2) (3 2)" +
                "(1 1) (2 1) (3 1)";
        Assert.assertEquals(expected, actualPrintableMapping);
    }

    @Test
    public void getCoordinateMapping() {
        Map<Coordinate, Integer> coordinateMapping = CoordinateMapping.getCoordinateMapping();

        //todo improve test
        Assert.assertEquals(9, coordinateMapping.size());
    }
}