package kt6;
public class GroupConstraints {
    private final int[] parent;
    private final int[] size;
    private final int[] parity;
    private boolean consistent = true;

    public GroupConstraints(int n) {
        parent = new int[n + 1];
        size = new int[n + 1];
        parity = new int[n + 1];
        for (int person = 1; person <= n; person++) {
            parent[person] = person;
            size[person] = 1;
        }
    }

    public boolean add(int u, int v, int relation) {
        if (!consistent) {
            return false;
        }
        int first = root(u);
        int second = root(v);
        if (first == second) {
            consistent = (parity[u] ^ parity[v]) == relation;
            return consistent;
        }

        // parity хранит XOR групп узла и его родителя; после root — узла и корня.
        int rootParity = parity[u] ^ parity[v] ^ relation;
        if (size[first] < size[second]) {
            int swap = first;
            first = second;
            second = swap;
        }
        parent[second] = first;
        parity[second] = rootParity;
        size[first] += size[second];
        return true;
    }

    private int root(int person) {
        if (parent[person] != person) {
            int previousParent = parent[person];
            parent[person] = root(previousParent);
            // При сжатии пути складываем отношения вдоль него операцией XOR.
            parity[person] ^= parity[previousParent];
        }
        return parent[person];
    }
}
