package kt7;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
    public static long[] find(int n, int[][] edges, int start) {
        WeightedGraph graph = WeightedGraph.directed(n, edges);
        long[] distance = new long[n + 1];
        Arrays.fill(distance, Long.MAX_VALUE);
        distance[start] = 0;
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingLong(State::distance));
        queue.add(new State(start, 0));

        while (!queue.isEmpty()) {
            State current = queue.remove();
            // В куче могут остаться записи, расстояние из которых уже улучшено.
            if (current.distance() != distance[current.vertex()]) {
                continue;
            }
            for (WeightedGraph.Edge edge : graph.edgesFrom(current.vertex())) {
                long candidate = current.distance() + edge.weight();
                if (candidate < distance[edge.to()]) {
                    distance[edge.to()] = candidate;
                    queue.add(new State(edge.to(), candidate));
                }
            }
        }
        return Arrays.copyOfRange(distance, 1, n + 1);
    }

    private record State(int vertex, long distance) {
    }
}
