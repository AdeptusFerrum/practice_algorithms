package kt7;
import java.util.ArrayDeque;
import java.util.Arrays;

public class NegativeCyclePaths {
    public static long[] find(int n, int[][] edges, int start) {
        long[] distance = new long[n + 1];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[start] = 0;
        for (int pass = 1; pass < n; pass++) {
            boolean changed = false;
            for (int[] edge : edges) {
                if (distance[edge[0]] != Long.MAX_VALUE
                        && distance[edge[0]] + edge[2] < distance[edge[1]]) {
                    distance[edge[1]] = distance[edge[0]] + edge[2];
                    changed = true;
                }
            }
            if (!changed) {
                break;
            }
        }

        boolean[] unbounded = new boolean[n + 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        // Улучшение после n - 1 проходов означает влияние достижимого отрицательного цикла.
        for (int[] edge : edges) {
            if (distance[edge[0]] != Long.MAX_VALUE
                    && distance[edge[0]] + edge[2] < distance[edge[1]] && !unbounded[edge[1]]) {
                unbounded[edge[1]] = true;
                queue.addLast(edge[1]);
            }
        }

        WeightedGraph graph = WeightedGraph.directed(n, edges);
        while (!queue.isEmpty()) {
            int vertex = queue.removeFirst();
            // Неограниченное снижение передаётся всем вершинам, достижимым из цикла.
            for (WeightedGraph.Edge edge : graph.edgesFrom(vertex)) {
                if (!unbounded[edge.to()]) {
                    unbounded[edge.to()] = true;
                    queue.addLast(edge.to());
                }
            }
        }
        for (int vertex = 1; vertex <= n; vertex++) {
            if (unbounded[vertex]) {
                distance[vertex] = Long.MIN_VALUE;
            }
        }
        return Arrays.copyOfRange(distance, 1, n + 1);
    }
}
