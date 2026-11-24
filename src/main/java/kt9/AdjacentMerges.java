package kt9;
public class AdjacentMerges {
    public static long minCost(int[] sizes) {
        int n = sizes.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + sizes[i];
        }
        long[][] cost = new long[n][n];
        for (int length = 2; length <= n; length++) {
            for (int left = 0; left + length <= n; left++) {
                int right = left + length - 1;
                long sum = prefix[right + 1] - prefix[left];
                long best = Long.MAX_VALUE;
                // Последнее объединение делит отрезок на две соседние группы.
                for (int split = left; split < right; split++) {
                    best = Math.min(best, cost[left][split] + cost[split + 1][right] + sum);
                }
                cost[left][right] = best;
            }
        }
        return cost[0][n - 1];
    }
}
