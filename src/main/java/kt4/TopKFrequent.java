package kt4;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequent {
    public static int[] find(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int number : nums) {
            counts.merge(number, 1, Integer::sum);
        }

        // В корне кучи худший кандидат: меньшая частота, а при равенстве — большее число.
        Comparator<Integer> order = Comparator.<Integer>comparingInt(counts::get)
                .thenComparing(Comparator.reverseOrder());
        PriorityQueue<Integer> best = new PriorityQueue<>(order);
        for (int number : counts.keySet()) {
            best.add(number);
            if (best.size() > k) {
                best.remove();
            }
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = best.remove();
        }
        return result;
    }
}
