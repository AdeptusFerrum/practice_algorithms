package kt7;
import java.util.Arrays;

public class BellmanFord {
    public static long[] find(int n, int[][] edges, int start) {
        long[] distance = new long[n + 1];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[start] = 0;
        // Без достижимых отрицательных циклов достаточно n - 1 проходов по рёбрам.
        for (int pass = 1; pass < n; pass++) {
            boolean changed = false;
            for (int[] edge : edges) {
                if (distance[edge[0]] == Long.MAX_VALUE) {
                    continue;
                }
                long candidate = distance[edge[0]] + edge[2];
                if (candidate < distance[edge[1]]) {
                    distance[edge[1]] = candidate;
                    changed = true;
                }
            }
            if (!changed) {
                break;
            }
        }
        return Arrays.copyOfRange(distance, 1, n + 1);
    }
}
