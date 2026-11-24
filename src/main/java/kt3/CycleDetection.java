package kt3;
public class CycleDetection {
    public static boolean hasCycle(int[] next, int start) {
        int slow = start;
        int fast = start;

        // Метод Флойда: один указатель делает шаг, второй два. В цикле они встретятся.
        // Номер узла начинается с 1, поэтому его следующий узел хранится в next[node - 1].
        while (fast != -1 && next[fast - 1] != -1) {
            slow = next[slow - 1];
            fast = next[next[fast - 1] - 1];
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
