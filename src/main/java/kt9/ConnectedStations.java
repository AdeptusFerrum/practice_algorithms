package kt9;
import java.util.Arrays;

public class ConnectedStations {
    public static long maxValue(int[] values, int[][] edges, int k) {
        int n = values.length;
        RootedTree tree = new RootedTree(n, edges);
        long[][] best = new long[n][k + 1];
        int[] size = new int[n];
        for (int vertex = 0; vertex < n; vertex++) {
            Arrays.fill(best[vertex], Long.MIN_VALUE);
            best[vertex][1] = values[vertex];
            size[vertex] = 1;
        }

        long answer = Long.MIN_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            int vertex = tree.order[i];
            // Набор обязан содержать свою верхнюю вершину, но ею может быть любая вершина дерева.
            answer = Math.max(answer, best[vertex][k]);
            int parent = tree.parent[vertex];
            if (parent == -1) {
                continue;
            }
            // Обратный проход не позволяет присоединить одно поддерево несколько раз.
            for (int taken = Math.min(size[parent], k - 1); taken >= 1; taken--) {
                for (int added = 1; added <= Math.min(size[vertex], k - taken); added++) {
                    best[parent][taken + added] = Math.max(best[parent][taken + added],
                            best[parent][taken] + best[vertex][added]);
                }
            }
            size[parent] += size[vertex];
        }
        return answer;
    }
}
