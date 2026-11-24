package kt5;
public class BlockedQueens {
    public static int count(int n, int[][] blockedCells) {
        int[] blocked = new int[n];
        for (int[] cell : blockedCells) {
            blocked[cell[0] - 1] |= 1 << (cell[1] - 1);
        }
        return place(0, 0, 0, 0, (1 << n) - 1, blocked);
    }

    private static int place(int row, int columns, int leftDiagonal, int rightDiagonal,
                             int boardMask, int[] blocked) {
        if (row == blocked.length) {
            return 1;
        }

        int available = boardMask & ~(columns | leftDiagonal | rightDiagonal | blocked[row]);
        int result = 0;
        while (available != 0) {
            int position = available & -available;
            available ^= position;
            // При переходе на следующую строку атакованные диагоналями клетки сдвигаются.
            result += place(row + 1, columns | position,
                    (leftDiagonal | position) << 1, (rightDiagonal | position) >>> 1,
                    boardMask, blocked);
        }
        return result;
    }
}
