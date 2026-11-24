package kt8;
public class ExpeditionKnapsack {
    public static long maxValue(int[][] items, int capacity) {
        long[] best = new long[capacity + 1];
        for (int[] item : items) {
            // Обратный проход не даёт взять текущий предмет повторно.
            for (int weight = capacity; weight >= item[0]; weight--) {
                best[weight] = Math.max(best[weight], best[weight - item[0]] + item[1]);
            }
        }
        return best[capacity];
    }
}
