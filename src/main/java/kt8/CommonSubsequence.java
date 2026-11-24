package kt8;
public class CommonSubsequence {
    public static int length(String first, String second) {
        if (first.length() < second.length()) {
            String swap = first;
            first = second;
            second = swap;
        }
        int[] best = new int[second.length() + 1];
        for (int i = 0; i < first.length(); i++) {
            int diagonal = 0;
            for (int j = 1; j <= second.length(); j++) {
                int above = best[j];
                if (first.charAt(i) == second.charAt(j - 1)) {
                    best[j] = diagonal + 1;
                } else {
                    best[j] = Math.max(best[j], best[j - 1]);
                }
                // Сохраняем старую диагональ до перезаписи следующей ячейки.
                diagonal = above;
            }
        }
        return best[second.length()];
    }
}
