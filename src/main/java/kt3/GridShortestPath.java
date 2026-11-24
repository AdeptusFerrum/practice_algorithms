package kt3;
import java.util.Arrays;

public class GridShortestPath {
    public static int find(char[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int start = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (grid[row][column] == 'S') {
                    start = row * columns + column;
                }
            }
        }

        int[] distance = new int[rows * columns];
        Arrays.fill(distance, -1);
        int[] queue = new int[rows * columns];
        int head = 0;
        int tail = 0;
        queue[tail++] = start;
        distance[start] = 0;
        int[] rowSteps = {-1, 1, 0, 0};
        int[] columnSteps = {0, 0, -1, 1};

        // BFS обходит клетки по расстоянию. В очереди храним номер row * columns + column.
        while (head < tail) {
            int cell = queue[head++];
            int row = cell / columns;
            int column = cell % columns;
            if (grid[row][column] == 'T') {
                return distance[cell];
            }

            for (int direction = 0; direction < 4; direction++) {
                int nextRow = row + rowSteps[direction];
                int nextColumn = column + columnSteps[direction];
                if (nextRow < 0 || nextRow >= rows || nextColumn < 0 || nextColumn >= columns) {
                    continue;
                }
                int nextCell = nextRow * columns + nextColumn;
                if (grid[nextRow][nextColumn] != '#' && distance[nextCell] == -1) {
                    distance[nextCell] = distance[cell] + 1;
                    queue[tail++] = nextCell;
                }
            }
        }
        return -1;
    }
}
