package kt10;
import java.util.concurrent.ThreadLocalRandom;

public class KthElement {
    public static int find(int[] values, int k) {
        int target = k - 1;
        int left = 0;
        int right = values.length - 1;
        while (left < right) {
            int pivot = values[ThreadLocalRandom.current().nextInt(left, right + 1)];
            int lower = left;
            int current = left;
            int upper = right;
            // Три части позволяют сразу исключить все значения, равные опорному.
            while (current <= upper) {
                if (values[current] < pivot) {
                    swap(values, current++, lower++);
                } else if (values[current] > pivot) {
                    swap(values, current, upper--);
                } else {
                    current++;
                }
            }
            if (target < lower) {
                right = lower - 1;
            } else if (target > upper) {
                left = upper + 1;
            } else {
                return pivot;
            }
        }
        return values[left];
    }

    private static void swap(int[] values, int a, int b) {
        int value = values[a];
        values[a] = values[b];
        values[b] = value;
    }
}
