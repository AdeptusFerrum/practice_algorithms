package kt4;
import java.util.Arrays;

public class TreeLevelSums {
    public static long[] calculate(int[][] nodes) {
        int[] queue = new int[nodes.length];
        long[] sums = new long[nodes.length];
        int head = 0;
        int tail = 0;
        int levels = 0;
        queue[tail++] = 0;

        while (head < tail) {
            // Фиксируем конец текущего уровня до добавления его потомков в очередь.
            int levelEnd = tail;
            long sum = 0;
            while (head < levelEnd) {
                int[] node = nodes[queue[head++]];
                sum += node[0];
                if (node[1] != -1) {
                    queue[tail++] = node[1];
                }
                if (node[2] != -1) {
                    queue[tail++] = node[2];
                }
            }
            sums[levels++] = sum;
        }
        return Arrays.copyOf(sums, levels);
    }
}
