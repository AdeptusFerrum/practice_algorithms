package kt8;
import java.util.Arrays;

public class IncreasingSubsequences {
    private static final int MOD = 1_000_000_007;

    public static Result find(int[] values) {
        int[] sorted = values.clone();
        Arrays.sort(sorted);
        int distinct = 0;
        for (int value : sorted) {
            if (distinct == 0 || sorted[distinct - 1] != value) {
                sorted[distinct++] = value;
            }
        }

        Fenwick tree = new Fenwick(distinct);
        for (int value : values) {
            int rank = Arrays.binarySearch(sorted, 0, distinct, value) + 1;
            // Берём только меньшие значения: равные не продолжают строгую подпоследовательность.
            Result prefix = tree.bestPrefix(rank - 1);
            int count = prefix.length() == 0 ? 1 : prefix.count();
            tree.add(rank, new Result(prefix.length() + 1, count));
        }
        return tree.bestPrefix(distinct);
    }

    public record Result(int length, int count) {
    }

    private static class Fenwick {
        private final int[] lengths;
        private final int[] counts;

        Fenwick(int size) {
            lengths = new int[size + 1];
            counts = new int[size + 1];
        }

        Result bestPrefix(int index) {
            int length = 0;
            int count = 0;
            for (int i = index; i > 0; i -= i & -i) {
                if (lengths[i] > length) {
                    length = lengths[i];
                    count = counts[i];
                } else if (lengths[i] == length) {
                    count = (count + counts[i]) % MOD;
                }
            }
            return new Result(length, count);
        }

        void add(int index, Result candidate) {
            // Каждый узел хранит лучшую длину и число способов получить её на своём отрезке.
            for (int i = index; i < lengths.length; i += i & -i) {
                if (candidate.length() > lengths[i]) {
                    lengths[i] = candidate.length();
                    counts[i] = candidate.count();
                } else if (candidate.length() == lengths[i]) {
                    counts[i] = (counts[i] + candidate.count()) % MOD;
                }
            }
        }
    }
}
