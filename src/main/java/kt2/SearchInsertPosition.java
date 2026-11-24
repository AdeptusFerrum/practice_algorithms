package kt2;
public class SearchInsertPosition {
    public static int find(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        // Ищем первую позицию с числом >= target в полуоткрытом диапазоне [left, right).
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }
}
