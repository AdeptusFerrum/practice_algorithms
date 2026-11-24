package kt7;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NearestHelpPointTest {
    @Test
    void findsNearestPointsAcrossUndirectedEdges() {
        NearestHelpPoint.Result[] result = NearestHelpPoint.find(5,
                new int[][]{{1, 2, 2}, {2, 3, 2}, {3, 4, 2}}, new int[]{4, 1});
        assertArrayEquals(new NearestHelpPoint.Result[]{
                new NearestHelpPoint.Result(1, 0), new NearestHelpPoint.Result(1, 2),
                new NearestHelpPoint.Result(4, 2), new NearestHelpPoint.Result(4, 0),
                new NearestHelpPoint.Result(-1, -1)}, result);
    }

    @Test
    void smallerPointWinsTieAndPropagatesToFollowingVertices() {
        int[][] edges = {{5, 3, 3}, {1, 2, 1}, {2, 3, 1}, {3, 4, 1}, {5, 3, 2}};
        NearestHelpPoint.Result[] result = NearestHelpPoint.find(5, edges, new int[]{5, 1});
        assertEquals(new NearestHelpPoint.Result(1, 2), result[2]);
        assertEquals(new NearestHelpPoint.Result(1, 3), result[3]);
    }

    @Test
    void eachPointChoosesItself() {
        assertArrayEquals(new NearestHelpPoint.Result[]{
                new NearestHelpPoint.Result(1, 0), new NearestHelpPoint.Result(2, 0)},
                NearestHelpPoint.find(2, new int[][]{{1, 2, 1}}, new int[]{2, 1}));
    }

    @Test
    void handlesEmptyGraphAndSinglePoint() {
        assertArrayEquals(new NearestHelpPoint.Result[]{new NearestHelpPoint.Result(1, 0)},
                NearestHelpPoint.find(1, new int[0][3], new int[]{1}));
        assertArrayEquals(new NearestHelpPoint.Result[]{
                new NearestHelpPoint.Result(-1, -1), new NearestHelpPoint.Result(2, 0)},
                NearestHelpPoint.find(2, new int[0][3], new int[]{2}));
    }

    @Test
    void preservesInputAndChoosesCheaperParallelEdge() {
        int[][] edges = {{1, 2, 9}, {1, 2, 3}, {2, 2, 1}};
        int[] points = {2};
        assertEquals(new NearestHelpPoint.Result(2, 3), NearestHelpPoint.find(2, edges, points)[0]);
        assertArrayEquals(new int[][]{{1, 2, 9}, {1, 2, 3}, {2, 2, 1}}, edges);
        assertArrayEquals(new int[]{2}, points);
    }

    @Test
    void handlesMaximumVertexCountAndLongDistances() {
        int n = 200_000;
        int[][] edges = new int[n - 1][3];
        for (int i = 1; i < n; i++) {
            edges[i - 1] = new int[]{i, i + 1, 1_000_000_000};
        }
        NearestHelpPoint.Result[] result = NearestHelpPoint.find(n, edges, new int[]{n, 1});
        for (int vertex = 1; vertex <= n; vertex++) {
            int point = vertex <= n / 2 ? 1 : n;
            long distance = Math.min(vertex - 1, n - vertex) * 1_000_000_000L;
            assertEquals(new NearestHelpPoint.Result(point, distance), result[vertex - 1]);
        }
    }
}
