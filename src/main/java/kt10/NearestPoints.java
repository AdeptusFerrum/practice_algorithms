package kt10;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class NearestPoints {
    private static final Comparator<Point> ORDER = Comparator.comparingLong(Point::distanceSquared)
            .thenComparingInt(Point::x)
            .thenComparingInt(Point::y)
            .thenComparingInt(Point::index);

    public static Point[] find(Point[] points, int k) {
        if (k < points.length) {
            int left = 0;
            int right = points.length - 1;
            while (left < right) {
                int pivot = partition(points, left, right);
                if (pivot == k - 1) {
                    break;
                }
                if (pivot < k - 1) {
                    left = pivot + 1;
                } else {
                    right = pivot - 1;
                }
            }
        }
        // Quickselect оставляет нужные точки в начале; сортируем только выбранные k.
        Point[] result = Arrays.copyOf(points, k);
        Arrays.sort(result, ORDER);
        return result;
    }

    private static int partition(Point[] points, int left, int right) {
        int pivotIndex = ThreadLocalRandom.current().nextInt(left, right + 1);
        Point pivot = points[pivotIndex];
        swap(points, pivotIndex, right);
        int boundary = left;
        for (int i = left; i < right; i++) {
            if (ORDER.compare(points[i], pivot) < 0) {
                swap(points, i, boundary++);
            }
        }
        swap(points, boundary, right);
        return boundary;
    }

    private static void swap(Point[] points, int a, int b) {
        Point point = points[a];
        points[a] = points[b];
        points[b] = point;
    }

    public record Point(int x, int y, int index) {
        long distanceSquared() {
            return (long) x * x + (long) y * y;
        }
    }
}
