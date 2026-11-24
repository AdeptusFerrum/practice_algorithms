package kt4;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KthPairSum {
    public static long find(int[] a, int[] b, int k) {
        int[] rows = a.length <= b.length ? a : b;
        int[] columns = a.length <= b.length ? b : a;
        PriorityQueue<Pair> smallest = new PriorityQueue<>(Comparator.comparingLong(Pair::sum));

        // Каждая строка сумм отсортирована. В куче хранится её ближайшая ещё не взятая пара.
        for (int row = 0; row < Math.min(rows.length, k); row++) {
            smallest.add(new Pair((long) rows[row] + columns[0], row, 0));
        }

        for (int position = 1; position < k; position++) {
            Pair pair = smallest.remove();
            int nextColumn = pair.column() + 1;
            if (nextColumn < columns.length) {
                smallest.add(new Pair((long) rows[pair.row()] + columns[nextColumn],
                        pair.row(), nextColumn));
            }
        }
        return smallest.element().sum();
    }

    private record Pair(long sum, int row, int column) {
    }
}
