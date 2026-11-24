package kt7;
import java.util.ArrayList;
import java.util.List;

class WeightedGraph {
    private final List<List<Edge>> adjacency;

    private WeightedGraph(int n) {
        adjacency = new ArrayList<>(n + 1);
        for (int vertex = 0; vertex <= n; vertex++) {
            adjacency.add(new ArrayList<>());
        }
    }

    static WeightedGraph directed(int n, int[][] edges) {
        WeightedGraph graph = new WeightedGraph(n);
        for (int[] edge : edges) {
            graph.adjacency.get(edge[0]).add(new Edge(edge[1], edge[2]));
        }
        return graph;
    }

    static WeightedGraph undirected(int n, int[][] edges) {
        WeightedGraph graph = new WeightedGraph(n);
        for (int[] edge : edges) {
            graph.adjacency.get(edge[0]).add(new Edge(edge[1], edge[2]));
            graph.adjacency.get(edge[1]).add(new Edge(edge[0], edge[2]));
        }
        return graph;
    }

    List<Edge> edgesFrom(int vertex) {
        return adjacency.get(vertex);
    }

    record Edge(int to, int weight) {
    }
}
