package kt8;
public class PlatformJumps {
    public static long minCost(int[] heights) {
        long previous = 0;
        long twoBack = 0;
        // На очередную платформу можно прийти только с двух предыдущих.
        for (int i = 1; i < heights.length; i++) {
            long cost = previous + Math.abs((long) heights[i] - heights[i - 1]);
            if (i >= 2) {
                cost = Math.min(cost, twoBack + Math.abs((long) heights[i] - heights[i - 2]));
            }
            twoBack = previous;
            previous = cost;
        }
        return previous;
    }
}
