package kt10;
import java.util.Arrays;
import java.util.Comparator;

public class IntervalScheduling {
    public static int maxCount(int[][] requests) {
        int[][] ordered = requests.clone();
        Arrays.sort(ordered, Comparator.comparingInt(request -> request[1]));
        int count = 0;
        int end = 0;
        for (int[] request : ordered) {
            // Самое раннее завершение оставляет больше места для следующих заявок.
            if (request[0] >= end) {
                count++;
                end = request[1];
            }
        }
        return count;
    }
}
