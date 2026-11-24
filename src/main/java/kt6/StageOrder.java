package kt6;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class StageOrder {
    public static int[] find(int n, int[][] requirements) {
        List<List<Integer>> next = new ArrayList<>(n + 1);
        for (int stage = 0; stage <= n; stage++) {
            next.add(new ArrayList<>());
        }
        int[] indegree = new int[n + 1];
        for (int[] requirement : requirements) {
            next.get(requirement[0]).add(requirement[1]);
            indegree[requirement[1]]++;
        }

        PriorityQueue<Integer> ready = new PriorityQueue<>();
        for (int stage = 1; stage <= n; stage++) {
            if (indegree[stage] == 0) {
                ready.add(stage);
            }
        }

        int[] order = new int[n];
        int count = 0;
        while (!ready.isEmpty()) {
            // Алгоритм Кана: из доступных этапов выбираем наименьший номер.
            int stage = ready.remove();
            order[count++] = stage;
            for (int following : next.get(stage)) {
                if (--indegree[following] == 0) {
                    ready.add(following);
                }
            }
        }
        // Необработанные этапы означают, что в графе есть цикл.
        return count == n ? order : new int[0];
    }
}
