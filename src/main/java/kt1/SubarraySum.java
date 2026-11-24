package kt1;
import java.util.HashMap;
import java.util.Map;

public class SubarraySum {
    public static long count(int[] nums, long k) {
        Map<Long, Integer> prefixCounts = new HashMap<>();
        prefixCounts.put(0L, 1); // Пустой префикс учитывает подмассивы с начала массива.
        long prefixSum = 0;
        long result = 0;

        for (int number : nums) {
            prefixSum += number;
            // Сумма участка равна разности двух префиксов, в том числе при отрицательных числах.
            result += prefixCounts.getOrDefault(prefixSum - k, 0);
            prefixCounts.merge(prefixSum, 1, Integer::sum);
        }
        return result;
    }
}
