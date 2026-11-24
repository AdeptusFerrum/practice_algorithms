package kt10;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class JobScheduling {
    public static long maxProfit(int[][] jobs) {
        int[][] ordered = jobs.clone();
        Arrays.sort(ordered, Comparator.comparingInt(job -> job[0]));
        PriorityQueue<Integer> profits = new PriorityQueue<>();
        long total = 0;
        for (int[] job : ordered) {
            profits.add(job[1]);
            total += job[1];
            // Если слотов не хватает, исключаем самый дешёвый из рассмотренных заказов.
            if (profits.size() > job[0]) {
                total -= profits.remove();
            }
        }
        return total;
    }
}
