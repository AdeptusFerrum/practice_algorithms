package kt3;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            printExamples();
            return;
        }
        if (args.length != 1) {
            throw new IllegalArgumentException("Pass one task number from 1 to 6");
        }

        InputReader input = new InputReader();
        switch (Integer.parseInt(args[0])) {
            case 1 -> {
                int n = input.nextInt();
                int[] days = NextHigherPrice.waitingDays(input.readArray(n));
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < days.length; i++) {
                    if (i > 0) {
                        output.append(' ');
                    }
                    output.append(days[i]);
                }
                System.out.println(output);
            }
            case 2 -> {
                int n = input.nextInt();
                int m = input.nextInt();
                System.out.println(GridShortestPath.find(input.readGrid(n, m)));
            }
            case 3 -> {
                int n = input.nextInt();
                int start = input.nextInt();
                System.out.println(CycleDetection.hasCycle(input.readArray(n), start) ? "YES" : "NO");
            }
            case 4 -> {
                int n = input.nextInt();
                int m = input.nextInt();
                System.out.println(MaximalRectangle.maxArea(input.readGrid(n, m)));
            }
            case 5 -> {
                int n = input.nextInt();
                int m = input.nextInt();
                System.out.println(KeyMaze.find(input.readGrid(n, m)));
            }
            case 6 -> {
                int n = input.nextInt();
                System.out.println(DuplicateNumber.find(input.readArray(n + 1)));
            }
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printExamples() {
        System.out.println("1. Next higher price: "
                + Arrays.toString(NextHigherPrice.waitingDays(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        System.out.println("2. Grid shortest path: "
                + GridShortestPath.find(new char[][]{
                "S..".toCharArray(),
                ".#.".toCharArray(),
                "..T".toCharArray()}));
        System.out.println("3. Cycle detection: "
                + (CycleDetection.hasCycle(new int[]{2, 3, 4, 2}, 1) ? "YES" : "NO"));
        System.out.println("4. Maximal rectangle: "
                + MaximalRectangle.maxArea(new char[][]{
                "10100".toCharArray(),
                "10111".toCharArray(),
                "11111".toCharArray(),
                "10010".toCharArray()}));
        System.out.println("5. Key maze: "
                + KeyMaze.find(new char[][]{"a.SAT".toCharArray()}));
        System.out.println("6. Duplicate number: "
                + DuplicateNumber.find(new int[]{1, 3, 4, 2, 2}));
    }

    private static class InputReader {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokens = new StringTokenizer("");

        private String next() throws IOException {
            while (!tokens.hasMoreTokens()) {
                String line = reader.readLine();
                if (line == null) {
                    throw new EOFException("Unexpected end of input");
                }
                tokens = new StringTokenizer(line);
            }
            return tokens.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        int[] readArray(int length) throws IOException {
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                values[i] = nextInt();
            }
            return values;
        }

        char[][] readGrid(int rows, int columns) throws IOException {
            char[][] grid = new char[rows][];
            for (int row = 0; row < rows; row++) {
                grid[row] = next().toCharArray();
                if (grid[row].length != columns) {
                    throw new IllegalArgumentException("Grid row has an unexpected length");
                }
            }
            return grid;
        }
    }
}
