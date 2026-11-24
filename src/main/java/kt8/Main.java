package kt8;
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
            case 1 -> System.out.println(PlatformJumps.minCost(input.readArray(input.nextInt())));
            case 2 -> {
                int n = input.nextInt();
                int capacity = input.nextInt();
                System.out.println(ExpeditionKnapsack.maxValue(input.readPairs(n), capacity));
            }
            case 3 -> System.out.println(CommonSubsequence.length(input.next(), input.next()));
            case 4 -> {
                int n = input.nextInt();
                int capacity = input.nextInt();
                ExactCargo.Result result = ExactCargo.find(input.readPairs(n), capacity);
                System.out.println(result == null ? "-1" : result.profit() + " " + result.count());
            }
            case 5 -> {
                IncreasingSubsequences.Result result = IncreasingSubsequences.find(input.readArray(input.nextInt()));
                System.out.println(result.length() + " " + result.count());
            }
            case 6 -> System.out.println(ShortestCommonSupersequence.find(input.next(), input.next()));
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printExamples() {
        System.out.println("1. Platform jumps: " + PlatformJumps.minCost(new int[]{10, 30, 40, 20}));
        System.out.println("2. Expedition knapsack: "
                + ExpeditionKnapsack.maxValue(new int[][]{{3, 30}, {4, 50}, {5, 60}}, 8));
        System.out.println("3. Common subsequence: " + CommonSubsequence.length("abcde", "ace"));
        ExactCargo.Result cargo = ExactCargo.find(new int[][]{{2, 4}, {3, 6}, {6, 12}}, 6);
        System.out.println("4. Exact cargo: " + cargo.profit() + " " + cargo.count());
        IncreasingSubsequences.Result increasing = IncreasingSubsequences.find(new int[]{1, 3, 5, 4, 7});
        System.out.println("5. Increasing subsequences: " + increasing.length() + " " + increasing.count());
        System.out.println("6. Shortest common supersequence: " + ShortestCommonSupersequence.find("ab", "ba"));
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

        int[][] readPairs(int count) throws IOException {
            int[][] pairs = new int[count][2];
            for (int[] pair : pairs) {
                pair[0] = nextInt();
                pair[1] = nextInt();
            }
            return pairs;
        }
    }
}
