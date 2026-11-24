package kt4;
import java.util.Comparator;
import java.util.PriorityQueue;

public class RunningMedian {
    public static double[] calculate(int[] nums) {
        PriorityQueue<Integer> lower = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> upper = new PriorityQueue<>();
        double[] medians = new double[nums.length];

        for (int i = 0; i < nums.length; i++) {
            if (lower.isEmpty() || nums[i] <= lower.element()) {
                lower.add(nums[i]);
            } else {
                upper.add(nums[i]);
            }

            // Нижняя половина содержит столько же чисел, сколько верхняя, или на одно больше.
            if (lower.size() > upper.size() + 1) {
                upper.add(lower.remove());
            } else if (lower.size() < upper.size()) {
                lower.add(upper.remove());
            }

            medians[i] = lower.size() == upper.size()
                    ? ((long) lower.element() + upper.element()) / 2.0
                    : lower.element();
        }
        return medians;
    }
}
