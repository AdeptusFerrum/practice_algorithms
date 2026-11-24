package kt1;
public class MaxWaterContainer {
    public static long maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        long maximum = 0;

        while (left < right) {
            long area = (long) (right - left) * Math.min(height[left], height[right]);
            maximum = Math.max(maximum, area);

            // Меньшая стенка ограничивает высоту: оставив её при меньшей ширине, ответ не улучшить.
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maximum;
    }
}
