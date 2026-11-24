package kt3;
import java.util.Arrays;

public class KeyMaze {
    public static int find(char[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[] keyBits = new int[10];
        int keyCount = 0;
        int start = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                char cell = grid[row][column];
                if (cell == 'S') {
                    start = row * columns + column;
                } else if (cell >= 'a' && cell <= 'j') {
                    keyBits[cell - 'a'] = 1 << keyCount++;
                }
            }
        }

        // Выделяем биты только для ключей на карте. Состояние BFS - клетка и набор ключей.
        int maskCount = 1 << keyCount;
        int[] distance = new int[rows * columns * maskCount];
        Arrays.fill(distance, -1);
        int[] queue = new int[distance.length];
        int head = 0;
        int tail = 0;
        int startState = start * maskCount;
        queue[tail++] = startState;
        distance[startState] = 0;
        int[] rowSteps = {-1, 1, 0, 0};
        int[] columnSteps = {0, 0, -1, 1};

        while (head < tail) {
            int state = queue[head++];
            int cell = state / maskCount;
            int keys = state % maskCount;
            int row = cell / columns;
            int column = cell % columns;
            if (grid[row][column] == 'T') {
                return distance[state];
            }

            for (int direction = 0; direction < 4; direction++) {
                int nextRow = row + rowSteps[direction];
                int nextColumn = column + columnSteps[direction];
                if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns) {
                    continue;
                }
                char nextCell = grid[nextRow][nextColumn];
                if (nextCell == '#') {
                    continue;
                }
                if (nextCell >= 'A' && nextCell <= 'J' && (keys & keyBits[nextCell - 'A']) == 0) {
                    continue;
                }

                int nextKeys = keys;
                if (nextCell >= 'a' && nextCell <= 'j') {
                    nextKeys |= keyBits[nextCell - 'a'];
                }
                int nextState = (nextRow * columns + nextColumn) * maskCount + nextKeys;
                if (distance[nextState] == -1) {
                    distance[nextState] = distance[state] + 1;
                    queue[tail++] = nextState;
                }
            }
        }
        return -1;
    }
}
