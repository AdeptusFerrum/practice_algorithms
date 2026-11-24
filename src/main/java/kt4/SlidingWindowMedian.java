package kt4;
import java.util.TreeMap;

public class SlidingWindowMedian {
    public static double[] calculate(int[] nums, int k) {
        Window window = new Window();
        double[] medians = new double[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            window.add(nums[i]);
            if (i >= k) {
                window.remove(nums[i - k]);
            }
            if (i >= k - 1) {
                medians[i - k + 1] = window.median();
            }
        }
        return medians;
    }

    private static class Window {
        // TreeMap хранит частоты и позволяет удалить уходящее из окна число за O(log k).
        private final TreeMap<Integer, Integer> lower = new TreeMap<>();
        private final TreeMap<Integer, Integer> upper = new TreeMap<>();
        private int lowerSize;
        private int upperSize;

        void add(int number) {
            if (lower.isEmpty() || number <= lower.lastKey()) {
                lower.merge(number, 1, Integer::sum);
                lowerSize++;
            } else {
                upper.merge(number, 1, Integer::sum);
                upperSize++;
            }
            balance();
        }

        void remove(int number) {
            if (lower.containsKey(number)) {
                removeOne(lower, number);
                lowerSize--;
            } else {
                removeOne(upper, number);
                upperSize--;
            }
            balance();
        }

        double median() {
            return lowerSize == upperSize
                    ? ((long) lower.lastKey() + upper.firstKey()) / 2.0
                    : lower.lastKey();
        }

        private void balance() {
            // Размеры считаются с повторами. В нижней половине допускается один лишний элемент.
            if (lowerSize > upperSize + 1) {
                int number = lower.lastKey();
                removeOne(lower, number);
                upper.merge(number, 1, Integer::sum);
                lowerSize--;
                upperSize++;
            } else if (lowerSize < upperSize) {
                int number = upper.firstKey();
                removeOne(upper, number);
                lower.merge(number, 1, Integer::sum);
                upperSize--;
                lowerSize++;
            }
        }

        private void removeOne(TreeMap<Integer, Integer> numbers, int number) {
            int count = numbers.get(number);
            if (count == 1) {
                numbers.remove(number);
            } else {
                numbers.put(number, count - 1);
            }
        }
    }
}
