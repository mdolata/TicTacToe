package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cells = scanner.nextLine();
        System.out.println(generateField(cells.replace("\"", "")));
    }

    static String generateField(String cells){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 9; i+=3) {
            String row = createRow(cells, i);
            result.append(String.format("| %s |\n", row));
        }
        return addHorizontal(result.toString().trim());
    }

    private static String addHorizontal(String field) {
        String horizontal = "---------";
        return String.format("%s\n%s\n%s", horizontal, field, horizontal );
    }

    private static String createRow(String cells, int i) {
        String substring = cells.substring(i, i + 3);
        String[] split = substring.split("");
        return String.format("%s %s %s", split[0], split[1], split[2]);
    }
}
