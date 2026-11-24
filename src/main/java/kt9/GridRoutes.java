package kt9;
public class GridRoutes {
    private static final int MOD = 1_000_000_007;

    public static int count(char[][] grid) {
        int[] ways = new int[grid[0].length];
        ways[0] = 1;
        for (char[] row : grid) {
            for (int column = 0; column < row.length; column++) {
                if (row[column] == '#') {
                    ways[column] = 0;
                } else if (column > 0) {
                    // В текущей ячейке ещё хранится ответ сверху, в предыдущей — уже слева.
                    ways[column] = (ways[column] + ways[column - 1]) % MOD;
                }
            }
        }
        return ways[ways.length - 1];
    }
}
