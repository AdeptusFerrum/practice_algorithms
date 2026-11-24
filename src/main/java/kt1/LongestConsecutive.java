package kt1;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutive {
    public static int length(int[] nums) {
        // Set убирает повторы и позволяет быстро проверять соседние числа.
        Set<Integer> numbers = new HashSet<>();
        for (int number : nums) {
            numbers.add(number);
        }

        int longest = 0;
        for (int number : numbers) {
            // Начинаем только с первого числа цепочки, чтобы не обходить её повторно.
            if (numbers.contains(number - 1)) {
                continue;
            }

            int current = number;
            int length = 1;
            while (numbers.contains(current + 1)) {
                current++;
                length++;
            }
            longest = Math.max(longest, length);
        }
        return longest;
    }
}
