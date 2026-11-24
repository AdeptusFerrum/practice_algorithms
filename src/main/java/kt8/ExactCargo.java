package kt8;
import java.util.Arrays;

public class ExactCargo {
    public static Result find(int[][] types, int capacity) {
        long[] profit = new long[capacity + 1];
        int[] count = new int[capacity + 1];
        // Нужна точная масса: изначально достижима только масса 0.
        Arrays.fill(profit, Long.MIN_VALUE);
        profit[0] = 0;
        for (int weight = 1; weight <= capacity; weight++) {
            for (int[] type : types) {
                int remaining = weight - type[0];
                if (remaining < 0 || profit[remaining] == Long.MIN_VALUE) {
                    continue;
                }
                long candidate = profit[remaining] + type[1];
                int candidateCount = count[remaining] + 1;
                if (candidate > profit[weight]
                        || candidate == profit[weight] && candidateCount < count[weight]) {
                    profit[weight] = candidate;
                    count[weight] = candidateCount;
                }
            }
        }
        return profit[capacity] == Long.MIN_VALUE ? null : new Result(profit[capacity], count[capacity]);
    }

    public record Result(long profit, int count) {
    }
}
