package kt5;
import java.util.ArrayList;
import java.util.List;

public class BalancedParentheses {
    public static List<String> generate(int n) {
        List<String> result = new ArrayList<>();
        build(new char[2 * n], 0, 0, n, result);
        return result;
    }

    private static void build(char[] sequence, int opened, int closed, int n, List<String> result) {
        if (closed == n) {
            result.add(new String(sequence));
            return;
        }

        // Сначала пробуем '(', поэтому готовые последовательности уже отсортированы.
        int position = opened + closed;
        if (opened < n) {
            sequence[position] = '(';
            build(sequence, opened + 1, closed, n, result);
        }
        if (closed < opened) {
            sequence[position] = ')';
            build(sequence, opened, closed + 1, n, result);
        }
    }
}
