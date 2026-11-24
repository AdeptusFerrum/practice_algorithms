package kt2;
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
                int target = input.nextInt();
                System.out.println(SearchInsertPosition.find(input.readArray(n), target));
            }
            case 2 -> {
                int n = input.nextInt();
                int[] nums = input.readArray(n);
                int q = input.nextInt();
                long[] sums = RangeSums.calculate(nums, input.readPairs(q));
                StringBuilder output = new StringBuilder();
                for (long sum : sums) {
                    output.append(sum).append('\n');
                }
                System.out.print(output);
            }
            case 3 -> {
                int n = input.nextInt();
                int[][] merged = MergeIntervals.merge(input.readPairs(n));
                StringBuilder output = new StringBuilder();
                for (int[] interval : merged) {
                    output.append(interval[0]).append(' ').append(interval[1]).append('\n');
                }
                System.out.print(output);
            }
            case 4 -> {
                int n = input.nextInt();
                int days = input.nextInt();
                System.out.println(ShipCapacity.minimum(input.readArray(n), days));
            }
            case 5 -> {
                int n = input.nextInt();
                long k = input.nextLong();
                System.out.println(SubarraySum.count(input.readArray(n), k));
            }
            case 6 -> {
                int n = input.nextInt();
                System.out.println(MeetingRooms.minimum(input.readPairs(n)));
            }
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printExamples() {
        System.out.println("1. Search insert position: "
                + SearchInsertPosition.find(new int[]{1, 3, 3, 5}, 3));
        System.out.println("2. Range sums: "
                + Arrays.toString(RangeSums.calculate(new int[]{2, -1, 3, 5},
                new int[][]{{0, 2}, {1, 3}, {2, 2}})));
        System.out.println("3. Merge intervals: "
                + Arrays.deepToString(MergeIntervals.merge(new int[][]{{5, 7}, {1, 3}, {3, 5}})));
        System.out.println("4. Ship capacity: "
                + ShipCapacity.minimum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5));
        System.out.println("5. Subarray sum: "
                + SubarraySum.count(new int[]{1, -1, 0}, 0));
        System.out.println("6. Meeting rooms: "
                + MeetingRooms.minimum(new int[][]{{0, 30}, {5, 10}, {15, 20}}));
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

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        int[] readArray(int length) throws IOException {
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                values[i] = nextInt();
            }
            return values;
        }

        int[][] readPairs(int length) throws IOException {
            int[][] pairs = new int[length][2];
            for (int i = 0; i < length; i++) {
                pairs[i][0] = nextInt();
                pairs[i][1] = nextInt();
            }
            return pairs;
        }
    }
}
