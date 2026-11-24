package kt4;
public class MaxTreePathSum {
    public static long find(int[][] nodes) {
        int[] order = new int[nodes.length];
        int count = 1;
        order[0] = 0;
        for (int i = 0; i < count; i++) {
            int[] node = nodes[order[i]];
            if (node[1] != -1) {
                order[count++] = node[1];
            }
            if (node[2] != -1) {
                order[count++] = node[2];
            }
        }

        long[] bestDown = new long[nodes.length];
        long maximum = Long.MIN_VALUE;
        // Обратный порядок позволяет обработать потомков раньше родителя без рекурсии.
        for (int i = count - 1; i >= 0; i--) {
            int index = order[i];
            int[] node = nodes[index];
            long left = node[1] == -1 ? 0 : Math.max(0, bestDown[node[1]]);
            long right = node[2] == -1 ? 0 : Math.max(0, bestDown[node[2]]);
            maximum = Math.max(maximum, node[0] + left + right);
            // К родителю можно продолжить только одну ветвь, иначе получится развилка.
            bestDown[index] = node[0] + Math.max(left, right);
        }
        return maximum;
    }
}
