package kt7;
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
        int n = input.nextInt();
        int m = input.nextInt();
        switch (Integer.parseInt(args[0])) {
            case 1 -> {
                int start = input.nextInt();
                printDistances(Dijkstra.find(n, input.readEdges(m), start));
            }
            case 2 -> {
                int start = input.nextInt();
                printDistances(BellmanFord.find(n, input.readEdges(m), start));
            }
            case 3 -> {
                int k = input.nextInt();
                int[] points = input.readArray(k);
                NearestHelpPoint.Result[] results = NearestHelpPoint.find(n, input.readEdges(m), points);
                StringBuilder output = new StringBuilder();
                for (NearestHelpPoint.Result result : results) {
                    output.append(result.point()).append(' ').append(result.distance()).append('\n');
                }
                System.out.print(output);
            }
            case 4 -> {
                int start = input.nextInt();
                int target = input.nextInt();
                System.out.println(DiscountedPath.find(n, input.readEdges(m), start, target));
            }
            case 5 -> {
                int start = input.nextInt();
                long[] distances = NegativeCyclePaths.find(n, input.readEdges(m), start);
                StringBuilder output = new StringBuilder();
                for (long distance : distances) {
                    output.append(formatState(distance)).append('\n');
                }
                System.out.print(output);
            }
            case 6 -> {
                int start = input.nextInt();
                int target = input.nextInt();
                int maxEdges = input.nextInt();
                BoundedPaths.Result result = BoundedPaths.find(n, input.readEdges(m), start, target, maxEdges);
                System.out.println(result == null ? "IMPOSSIBLE" : result.cost() + " " + result.count());
            }
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printDistances(long[] distances) {
        StringBuilder output = new StringBuilder();
        for (long distance : distances) {
            if (!output.isEmpty()) {
                output.append(' ');
            }
            output.append(distance == Long.MAX_VALUE ? "INF" : Long.toString(distance));
        }
        System.out.println(output);
    }

    private static String formatState(long distance) {
        if (distance == Long.MAX_VALUE) {
            return "*";
        }
        if (distance == Long.MIN_VALUE) {
            return "-";
        }
        return Long.toString(distance);
    }

    private static void printExamples() {
        System.out.print("1. Dijkstra: ");
        printDistances(Dijkstra.find(5, new int[][]{{1, 2, 4}, {1, 3, 1}, {3, 2, 2}, {2, 4, 1}}, 1));
        System.out.print("2. Bellman-Ford: ");
        printDistances(BellmanFord.find(4, new int[][]{{1, 2, 4}, {1, 3, 5}, {2, 3, -2}}, 1));
        NearestHelpPoint.Result nearest = NearestHelpPoint.find(5,
                new int[][]{{1, 2, 2}, {2, 3, 2}, {3, 4, 2}}, new int[]{4, 1})[1];
        System.out.println("3. Nearest help point (vertex 2): " + nearest.point() + " " + nearest.distance());
        System.out.println("4. Discounted path: "
                + DiscountedPath.find(3, new int[][]{{1, 2, 5}, {2, 3, 5}, {1, 3, 20}}, 1, 3));
        System.out.print("5. Negative cycles:");
        for (long distance : NegativeCyclePaths.find(5,
                new int[][]{{1, 2, 1}, {2, 3, -2}, {3, 2, 1}, {1, 4, 5}}, 1)) {
            System.out.print(" " + formatState(distance));
        }
        System.out.println();
        BoundedPaths.Result bounded = BoundedPaths.find(3,
                new int[][]{{1, 3, 2}, {1, 3, 2}, {1, 2, 1}, {2, 3, 1}}, 1, 3, 2);
        System.out.println("6. Bounded paths: " + bounded.cost() + " " + bounded.count());
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

        int[][] readEdges(int count) throws IOException {
            int[][] edges = new int[count][3];
            for (int[] edge : edges) {
                edge[0] = nextInt();
                edge[1] = nextInt();
                edge[2] = nextInt();
            }
            return edges;
        }
    }
}
