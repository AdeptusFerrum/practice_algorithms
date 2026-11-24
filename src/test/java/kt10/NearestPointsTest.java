package kt10;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NearestPointsTest {
    @Test
    void ordersByDistanceThenXThenYThenOriginalIndex() {
        NearestPoints.Point[] points = {
                new NearestPoints.Point(1, 0, 1), new NearestPoints.Point(-1, 0, 2),
                new NearestPoints.Point(0, 1, 3), new NearestPoints.Point(0, -1, 4),
                new NearestPoints.Point(-1, 0, 5), new NearestPoints.Point(0, 0, 6)
        };
        assertArrayEquals(new NearestPoints.Point[]{points[5], points[1], points[4], points[3]},
                NearestPoints.find(points.clone(), 4));
    }

    @Test
    void usesOriginalIndexWhenEqualCoordinatesCrossSelectionBoundary() {
        NearestPoints.Point[] points = {
                new NearestPoints.Point(3, 4, 1), new NearestPoints.Point(0, 0, 2),
                new NearestPoints.Point(3, 4, 3), new NearestPoints.Point(3, 4, 4)
        };
        assertArrayEquals(new NearestPoints.Point[]{points[1], points[0]},
                NearestPoints.find(points, 2));
    }

    @Test
    void computesSquaredDistancesInLong() {
        NearestPoints.Point[] points = {
                new NearestPoints.Point(1_000_000_000, 1_000_000_000, 1),
                new NearestPoints.Point(-1_000_000_000, -1_000_000_000, 2),
                new NearestPoints.Point(1_000_000_000, 0, 3),
                new NearestPoints.Point(0, -1_000_000_000, 4),
                new NearestPoints.Point(0, 0, 5)
        };
        assertArrayEquals(new NearestPoints.Point[]{points[4], points[3], points[2], points[1]},
                NearestPoints.find(points, 4));
    }

    @Test
    void handlesSinglePointAndSelectionOfAllPoints() {
        NearestPoints.Point point = new NearestPoints.Point(7, -3, 1);
        assertArrayEquals(new NearestPoints.Point[]{point}, NearestPoints.find(new NearestPoints.Point[]{point}, 1));
        NearestPoints.Point[] points = {
                new NearestPoints.Point(2, 0, 1), new NearestPoints.Point(-1, 0, 2),
                new NearestPoints.Point(0, 0, 3)
        };
        assertArrayEquals(new NearestPoints.Point[]{points[2], points[1], points[0]}, NearestPoints.find(points, 3));
    }

    @Test
    void handlesMaximumInputAndResultSizes() {
        int n = 2_000_000;
        NearestPoints.Point[] points = new NearestPoints.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new NearestPoints.Point(n - i, 0, i + 1);
        }
        NearestPoints.Point[] result = NearestPoints.find(points, 100_000);
        assertEquals(100_000, result.length);
        for (int i = 0; i < result.length; i++) {
            assertEquals(new NearestPoints.Point(i + 1, 0, n - i), result[i]);
        }
    }

    @Test
    void handlesManyIdenticalCoordinates() {
        NearestPoints.Point[] points = new NearestPoints.Point[200_000];
        for (int i = 0; i < points.length; i++) {
            points[i] = new NearestPoints.Point(7, -3, i + 1);
        }
        NearestPoints.Point[] result = NearestPoints.find(points, 100_000);
        assertEquals(100_000, result.length);
        for (int i = 0; i < result.length; i++) {
            assertEquals(new NearestPoints.Point(7, -3, i + 1), result[i]);
        }
    }

    @Test
    void matchesFullSortingAndPreservesAllInputPoints() {
        Random random = new Random(42);
        for (int test = 0; test < 200; test++) {
            NearestPoints.Point[] points = new NearestPoints.Point[1 + random.nextInt(100)];
            for (int i = 0; i < points.length; i++) {
                points[i] = new NearestPoints.Point(random.nextInt(9) - 4, random.nextInt(9) - 4, i + 1);
            }
            NearestPoints.Point[] sorted = points.clone();
            Arrays.sort(sorted, this::compare);
            int k = 1 + random.nextInt(points.length);
            assertArrayEquals(Arrays.copyOf(sorted, k), NearestPoints.find(points, k));
            Arrays.sort(points, this::compare);
            assertArrayEquals(sorted, points);
        }
    }

    private int compare(NearestPoints.Point a, NearestPoints.Point b) {
        long distanceA = (long) a.x() * a.x() + (long) a.y() * a.y();
        long distanceB = (long) b.x() * b.x() + (long) b.y() * b.y();
        if (distanceA != distanceB) {
            return Long.compare(distanceA, distanceB);
        }
        if (a.x() != b.x()) {
            return Integer.compare(a.x(), b.x());
        }
        if (a.y() != b.y()) {
            return Integer.compare(a.y(), b.y());
        }
        return Integer.compare(a.index(), b.index());
    }
}
