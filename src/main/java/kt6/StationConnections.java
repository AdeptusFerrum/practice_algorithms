package kt6;
public class StationConnections {
    private final int[] parent;
    private final int[] size;

    public StationConnections(int n) {
        parent = new int[n + 1];
        size = new int[n + 1];
        for (int station = 1; station <= n; station++) {
            parent[station] = station;
            size[station] = 1;
        }
    }

    public void connect(int u, int v) {
        int first = root(u);
        int second = root(v);
        if (first == second) {
            return;
        }
        // Меньшую компоненту присоединяем к большей, чтобы деревья оставались неглубокими.
        if (size[first] < size[second]) {
            int swap = first;
            first = second;
            second = swap;
        }
        parent[second] = first;
        size[first] += size[second];
    }

    public boolean connected(int u, int v) {
        return root(u) == root(v);
    }

    private int root(int station) {
        while (parent[station] != station) {
            // Сжатие пути сокращает последующие поиски представителя компоненты.
            parent[station] = parent[parent[station]];
            station = parent[station];
        }
        return station;
    }
}
