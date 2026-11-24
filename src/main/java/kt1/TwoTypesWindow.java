package kt1;
import java.util.HashMap;
import java.util.Map;

public class TwoTypesWindow {
    public static int maxLength(int[] items) {
        // Храним частоты только внутри окна: его границы должны оставаться непрерывными.
        Map<Integer, Integer> counts = new HashMap<>();
        int left = 0;
        int longest = 0;

        for (int right = 0; right < items.length; right++) {
            counts.merge(items[right], 1, Integer::sum);
            while (counts.size() > 2) {
                int type = items[left++];
                int remaining = counts.get(type) - 1;
                if (remaining == 0) {
                    counts.remove(type);
                } else {
                    counts.put(type, remaining);
                }
            }
            longest = Math.max(longest, right - left + 1);
        }
        return longest;
    }
}
