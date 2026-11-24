package kt9;
public class IndependentStations {
    public static long maxValue(int[] values, int[][] edges) {
        RootedTree tree = new RootedTree(values.length, edges);
        long[] included = new long[values.length];
        long[] excluded = new long[values.length];
        for (int vertex = 0; vertex < values.length; vertex++) {
            included[vertex] = values[vertex];
        }
        for (int i = tree.order.length - 1; i > 0; i--) {
            int vertex = tree.order[i];
            int parent = tree.parent[vertex];
            // Если родитель выбран, ребёнок исключается; иначе допустим лучший из двух вариантов.
            included[parent] += excluded[vertex];
            excluded[parent] += Math.max(included[vertex], excluded[vertex]);
        }
        return Math.max(included[0], excluded[0]);
    }
}
