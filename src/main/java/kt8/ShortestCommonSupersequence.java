package kt8;
public class ShortestCommonSupersequence {
    public static String find(String first, String second) {
        int n = first.length();
        int m = second.length();
        // Длины ответов для суффиксов нужны, чтобы затем восстановить строку слева направо.
        int[][] length = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            length[i][m] = n - i;
        }
        for (int j = 0; j <= m; j++) {
            length[n][j] = m - j;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                length[i][j] = 1 + (first.charAt(i) == second.charAt(j)
                        ? length[i + 1][j + 1] : Math.min(length[i + 1][j], length[i][j + 1]));
            }
        }

        StringBuilder result = new StringBuilder(length[0][0]);
        int i = 0;
        int j = 0;
        while (i < n && j < m) {
            char a = first.charAt(i);
            char b = second.charAt(j);
            if (a == b) {
                result.append(a);
                i++;
                j++;
            } else if (length[i + 1][j] < length[i][j + 1]
                    || length[i + 1][j] == length[i][j + 1] && a < b) {
                // При равной длине выбираем меньший первый символ.
                result.append(a);
                i++;
            } else {
                result.append(b);
                j++;
            }
        }
        result.append(first, i, n);
        result.append(second, j, m);
        return result.toString();
    }
}
