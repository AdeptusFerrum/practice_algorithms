package kt7;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DiscountedPath {
    public static long find(int n, int[][] edges, int start, int target) {
        WeightedGraph graph = WeightedGraph.directed(n, edges);
        // Для каждой вершины храним два состояния: купон свободен или уже использован.
        long[][] distance = new long[2][n + 1];
        Arrays.fill(distance[0], Long.MAX_VALUE);
        Arrays.fill(distance[1], Long.MAX_VALUE);
        distance[0][start] = 0;
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingLong(State::distance));
        queue.add(new State(start, 0, 0));

        while (!queue.isEmpty()) {
            State current = queue.remove();
            if (current.distance() != distance[current.used()][current.vertex()]) {
                continue;
            }
            if (current.vertex() == target) {
                return current.distance();
            }
            for (WeightedGraph.Edge edge : graph.edgesFrom(current.vertex())) {
                long candidate = current.distance() + edge.weight();
                if (candidate < distance[current.used()][edge.to()]) {
                    distance[current.used()][edge.to()] = candidate;
                    queue.add(new State(edge.to(), current.used(), candidate));
                }
                if (current.used() == 0) {
                    long discounted = current.distance() + edge.weight() / 2;
                    if (discounted < distance[1][edge.to()]) {
                        distance[1][edge.to()] = discounted;
                        queue.add(new State(edge.to(), 1, discounted));
                    }
                }
            }
        }
        return -1;
    }

    private record State(int vertex, int used, long distance) {
    }
}
