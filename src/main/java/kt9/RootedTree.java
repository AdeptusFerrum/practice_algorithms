package kt9;
import java.util.ArrayList;
import java.util.List;

class RootedTree {
    final int[] parent;
    final int[] order;

    RootedTree(int n, int[][] edges) {
        List<List<Integer>> neighbors = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            neighbors.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int a = edge[0] - 1;
            int b = edge[1] - 1;
            neighbors.get(a).add(b);
            neighbors.get(b).add(a);
        }

        parent = new int[n];
        parent[0] = -1;
        order = new int[n];
        int size = 1;
        // Массив порядка служит очередью: глубокое дерево не требует рекурсивных вызовов.
        for (int i = 0; i < size; i++) {
            int vertex = order[i];
            for (int next : neighbors.get(vertex)) {
                if (next != parent[vertex]) {
                    parent[next] = vertex;
                    order[size++] = next;
                }
            }
        }
    }
}
