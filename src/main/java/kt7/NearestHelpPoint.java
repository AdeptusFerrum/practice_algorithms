package kt7;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class NearestHelpPoint {
    public static Result[] find(int n, int[][] edges, int[] points) {
        WeightedGraph graph = WeightedGraph.undirected(n, edges);
        long[] distance = new long[n + 1];
        Arrays.fill(distance, Long.MAX_VALUE);
        int[] nearest = new int[n + 1];
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingLong(State::distance)
                .thenComparingInt(State::point));
        // Дейкстра начинается сразу во всех пунктах помощи.
        for (int point : points) {
            distance[point] = 0;
            nearest[point] = point;
            queue.add(new State(point, point, 0));
        }

        while (!queue.isEmpty()) {
            State current = queue.remove();
            if (current.distance() != distance[current.vertex()]
                    || current.point() != nearest[current.vertex()]) {
                continue;
            }
            for (WeightedGraph.Edge edge : graph.edgesFrom(current.vertex())) {
                long candidate = current.distance() + edge.weight();
                // Номер пункта уточняет ответ при равных расстояниях.
                if (candidate < distance[edge.to()]
                        || candidate == distance[edge.to()] && current.point() < nearest[edge.to()]) {
                    distance[edge.to()] = candidate;
                    nearest[edge.to()] = current.point();
                    queue.add(new State(edge.to(), current.point(), candidate));
                }
            }
        }

        Result[] result = new Result[n];
        for (int vertex = 1; vertex <= n; vertex++) {
            result[vertex - 1] = distance[vertex] == Long.MAX_VALUE
                    ? new Result(-1, -1) : new Result(nearest[vertex], distance[vertex]);
        }
        return result;
    }

    public record Result(int point, long distance) {
    }

    private record State(int vertex, int point, long distance) {
    }
}
