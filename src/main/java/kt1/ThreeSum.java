package kt1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public static List<List<Integer>> find(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        List<List<Integer>> triples = new ArrayList<>();

        for (int first = 0; first < sorted.length - 2; first++) {
            if (sorted[first] > 0) {
                break;
            }
            if (first > 0 && sorted[first] == sorted[first - 1]) {
                continue;
            }

            int left = first + 1;
            int right = sorted.length - 1;
            while (left < right) {
                int sum = sorted[first] + sorted[left] + sorted[right];
                // После сортировки сдвиг левой границы увеличивает сумму, правой — уменьшает.
                if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    triples.add(List.of(sorted[first], sorted[left], sorted[right]));
                    int leftValue = sorted[left];
                    int rightValue = sorted[right];
                    // Пропускаем одинаковые пары, чтобы не хранить дубликаты троек.
                    while (left < right && sorted[left] == leftValue) {
                        left++;
                    }
                    while (left < right && sorted[right] == rightValue) {
                        right--;
                    }
                }
            }
        }
        return triples;
    }
}
