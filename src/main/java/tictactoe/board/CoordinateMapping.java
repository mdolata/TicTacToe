package tictactoe.board;

import tictactoe.util.Tuple;

import java.util.*;

public class CoordinateMapping {
    private static final Map<Coordinate, Integer> COORDINATE_MAPPING = createCoordinateMapping();
    private static final List<Tuple<Coordinate, Integer>> COORDINATE_TUPLE = createCoordinateTuple();

    private static List<Tuple<Coordinate, Integer>> createCoordinateTuple() {
        List<Tuple<Coordinate, Integer>> list = new ArrayList<>();
        list.add(Tuple.of(Coordinate.fromString("1 3").getRight(), 0));
        list.add(Tuple.of(Coordinate.fromString("2 3").getRight(), 1));
        list.add(Tuple.of(Coordinate.fromString("3 3").getRight(), 2));
        list.add(Tuple.of(Coordinate.fromString("1 2").getRight(), 3));
        list.add(Tuple.of(Coordinate.fromString("2 2").getRight(), 4));
        list.add(Tuple.of(Coordinate.fromString("3 2").getRight(), 5));
        list.add(Tuple.of(Coordinate.fromString("1 1").getRight(), 6));
        list.add(Tuple.of(Coordinate.fromString("2 1").getRight(), 7));
        list.add(Tuple.of(Coordinate.fromString("3 1").getRight(), 8));
        return list;
    }

    public static String getPrintableMapping() {
        return String.format("%s %s %s" +
                "%s %s %s" +
                "%s %s %s", COORDINATE_TUPLE.stream()
                .sorted(Comparator.comparing(Tuple::get_2))
                .map(Tuple::get_1)
                .toArray());
    }

    public static Map<Coordinate, Integer> getCoordinateMapping() {
        return COORDINATE_MAPPING;
    }


    private static Map<Coordinate, Integer> createCoordinateMapping() {
        Map<Coordinate, Integer> map = new HashMap<>(9);
        map.put(Coordinate.fromString("1 1").getRight(), 6);
        map.put(Coordinate.fromString("1 2").getRight(), 3);
        map.put(Coordinate.fromString("1 3").getRight(), 0);
        map.put(Coordinate.fromString("2 1").getRight(), 7);
        map.put(Coordinate.fromString("2 2").getRight(), 4);
        map.put(Coordinate.fromString("2 3").getRight(), 1);
        map.put(Coordinate.fromString("3 1").getRight(), 8);
        map.put(Coordinate.fromString("3 2").getRight(), 5);
        map.put(Coordinate.fromString("3 3").getRight(), 2);
        return map;
    }
}
