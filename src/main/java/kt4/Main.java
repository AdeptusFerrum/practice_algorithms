package kt4;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
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
                long[] sums = TreeLevelSums.calculate(input.readNodes(n));
                StringBuilder output = new StringBuilder();
                for (long sum : sums) {
                    if (!output.isEmpty()) {
                        output.append(' ');
                    }
                    output.append(sum);
                }
                System.out.println(output);
            }
            case 2 -> {
                int n = input.nextInt();
                int k = input.nextInt();
                int[] numbers = TopKFrequent.find(input.readArray(n), k);
                StringBuilder output = new StringBuilder();
                for (int number : numbers) {
                    if (!output.isEmpty()) {
                        output.append(' ');
                    }
                    output.append(number);
                }
                System.out.println(output);
            }
            case 3 -> {
                int n = input.nextInt();
                printMedians(RunningMedian.calculate(input.readArray(n)));
            }
            case 4 -> {
                int n = input.nextInt();
                System.out.println(MaxTreePathSum.find(input.readNodes(n)));
            }
            case 5 -> {
                int n = input.nextInt();
                int m = input.nextInt();
                int k = input.nextInt();
                int[] a = input.readArray(n);
                int[] b = input.readArray(m);
                System.out.println(KthPairSum.find(a, b, k));
            }
            case 6 -> {
                int n = input.nextInt();
                int k = input.nextInt();
                printMedians(SlidingWindowMedian.calculate(input.readArray(n), k));
            }
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printMedians(double[] medians) {
        DecimalFormat format = new DecimalFormat("0.0", DecimalFormatSymbols.getInstance(Locale.ROOT));
        StringBuilder output = new StringBuilder();
        for (double median : medians) {
            if (!output.isEmpty()) {
                output.append(' ');
            }
            output.append(format.format(median));
        }
        System.out.println(output);
    }

    private static void printExamples() {
        int[][] tree = {{-10, 1, 2}, {9, -1, -1}, {20, 3, 4}, {15, -1, -1}, {7, -1, -1}};
        System.out.println("1. Tree level sums: " + Arrays.toString(TreeLevelSums.calculate(tree)));
        System.out.println("2. Top K frequent: "
                + Arrays.toString(TopKFrequent.find(new int[]{1, 1, 1, 2, 2, 3}, 2)));
        System.out.println("3. Running median: "
                + Arrays.toString(RunningMedian.calculate(new int[]{1, 2, 3, 4})));
        System.out.println("4. Max tree path sum: " + MaxTreePathSum.find(tree));
        System.out.println("5. Kth pair sum: "
                + KthPairSum.find(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 4));
        System.out.println("6. Sliding window median: "
                + Arrays.toString(SlidingWindowMedian.calculate(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
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

        int[][] readNodes(int count) throws IOException {
            int[][] nodes = new int[count][3];
            for (int[] node : nodes) {
                node[0] = nextInt();
                node[1] = nextInt();
                node[2] = nextInt();
            }
            return nodes;
        }
    }
}
