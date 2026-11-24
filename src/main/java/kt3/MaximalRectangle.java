package kt3;
public class MaximalRectangle {
    public static int maxArea(char[][] matrix) {
        int columns = matrix[0].length;
        int[] heights = new int[columns];
        int[] stack = new int[columns];
        int maximum = 0;

        for (char[] row : matrix) {
            // Высота столбца равна числу единиц подряд, заканчивающихся на текущей строке.
            for (int column = 0; column < columns; column++) {
                heights[column] = row[column] == '1' ? heights[column] + 1 : 0;
            }

            int size = 0;
            for (int column = 0; column <= columns; column++) {
                int height = column == columns ? 0 : heights[column];
                while (size > 0 && heights[stack[size - 1]] > height) {
                    int rectangleHeight = heights[stack[--size]];
                    int left = size == 0 ? -1 : stack[size - 1];
                    // Текущий столбец и оставшийся верх стека ограничивают ширину прямоугольника.
                    int width = column - left - 1;
                    maximum = Math.max(maximum, rectangleHeight * width);
                }
                if (column < columns) {
                    stack[size++] = column;
                }
            }
        }
        return maximum;
    }
}
