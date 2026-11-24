package kt2;
public class RangeSums {
    public static long[] calculate(int[] nums, int[][] queries) {
        long[] prefix = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        long[] sums = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            // Префиксы позволяют отвечать за O(1); right включён в запрошенный отрезок.
            sums[i] = prefix[right + 1] - prefix[left];
        }
        return sums;
    }
}
