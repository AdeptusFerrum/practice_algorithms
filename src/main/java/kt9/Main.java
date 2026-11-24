package kt9;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
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
            case 1 -> System.out.println(PalindromePartition.minParts(input.next()));
            case 2 -> {
                int n = input.nextInt();
                int m = input.nextInt();
                char[][] grid = new char[n][];
                for (int i = 0; i < n; i++) {
                    grid[i] = input.next().toCharArray();
                    if (grid[i].length != m) {
                        throw new IllegalArgumentException("Unexpected row length");
                    }
                }
                System.out.println(GridRoutes.count(grid));
            }
            case 3 -> {
                int n = input.nextInt();
                int[] values = input.readArray(n);
                System.out.println(IndependentStations.maxValue(values, input.readMatrix(n - 1, 2)));
            }
            case 4 -> System.out.println(AdjacentMerges.minCost(input.readArray(input.nextInt())));
            case 5 -> {
                int n = input.nextInt();
                int m = input.nextInt();
                int k = input.nextInt();
                System.out.println(ShieldedGrid.maxSum(input.readMatrix(n, m), k));
            }
            case 6 -> {
                int n = input.nextInt();
                int k = input.nextInt();
                int[] values = input.readArray(n);
                System.out.println(ConnectedStations.maxValue(values, input.readMatrix(n - 1, 2), k));
            }
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printExamples() {
        System.out.println("1. Palindrome partition: " + PalindromePartition.minParts("aab"));
        System.out.println("2. Grid routes: "
                + GridRoutes.count(new char[][]{"...".toCharArray(), ".#.".toCharArray(), "...".toCharArray()}));
        System.out.println("3. Independent stations: "
                + IndependentStations.maxValue(new int[]{5, 1, 4, 10}, new int[][]{{1, 2}, {1, 3}, {2, 4}}));
        System.out.println("4. Adjacent merges: " + AdjacentMerges.minCost(new int[]{10, 20, 30}));
        System.out.println("5. Shielded grid: " + ShieldedGrid.maxSum(new int[][]{{1, -2}, {-3, 4}}, 1));
        System.out.println("6. Connected stations: "
                + ConnectedStations.maxValue(new int[]{-10, 5, 7, 3}, new int[][]{{1, 2}, {2, 3}, {2, 4}}, 2));
    }

    private static class InputReader {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokens = new StringTokenizer("");

        String next() throws IOException {
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

        int[][] readMatrix(int rows, int columns) throws IOException {
            int[][] values = new int[rows][];
            for (int i = 0; i < rows; i++) {
                values[i] = readArray(columns);
            }
            return values;
        }
    }
}
