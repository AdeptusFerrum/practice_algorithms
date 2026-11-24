package kt7;
import java.util.Arrays;

public class BoundedPaths {
    private static final int MOD = 1_000_000_007;

    public static Result find(int n, int[][] edges, int start, int target, int maxEdges) {
        long[] previous = new long[n + 1];
        long[] next = new long[n + 1];
        int[] previousCount = new int[n + 1];
        int[] nextCount = new int[n + 1];
        Arrays.fill(previous, Long.MAX_VALUE);
        previous[start] = 0;
        previousCount[start] = 1;
        long best = start == target ? 0 : Long.MAX_VALUE;
        int count = start == target ? 1 : 0;

        // Каждый слой содержит маршруты ровно из заданного числа рёбер.
        for (int length = 1; length <= maxEdges; length++) {
            Arrays.fill(next, Long.MAX_VALUE);
            Arrays.fill(nextCount, 0);
            for (int[] edge : edges) {
                if (previous[edge[0]] == Long.MAX_VALUE) {
                    continue;
                }
                long candidate = previous[edge[0]] + edge[2];
                if (candidate < next[edge[1]]) {
                    next[edge[1]] = candidate;
                    nextCount[edge[1]] = previousCount[edge[0]];
                } else if (candidate == next[edge[1]]) {
                    nextCount[edge[1]] = (nextCount[edge[1]] + previousCount[edge[0]]) % MOD;
                }
            }
            // Объединяем ответы разных длин, не перенося старые маршруты в новый слой.
            if (next[target] < best) {
                best = next[target];
                count = nextCount[target];
            } else if (next[target] == best && best != Long.MAX_VALUE) {
                count = (count + nextCount[target]) % MOD;
            }

            long[] swapDistance = previous;
            previous = next;
            next = swapDistance;
            int[] swapCount = previousCount;
            previousCount = nextCount;
            nextCount = swapCount;
        }
        return best == Long.MAX_VALUE ? null : new Result(best, count);
    }

    public record Result(long cost, int count) {
    }
}
