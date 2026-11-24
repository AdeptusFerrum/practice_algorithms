package kt2;
public class ShipCapacity {
    public static long minimum(int[] weights, int days) {
        long left = 0;
        long right = 0;
        for (int weight : weights) {
            left = Math.max(left, weight);
            right += weight;
        }

        // Если вместимости хватает, любой больший корабль тоже подходит: применяем бинарный поиск.
        while (left < right) {
            long capacity = left + (right - left) / 2;
            if (canShip(weights, days, capacity)) {
                right = capacity;
            } else {
                left = capacity + 1;
            }
        }
        return left;
    }

    private static boolean canShip(int[] weights, int days, long capacity) {
        int usedDays = 1;
        long load = 0;
        for (int weight : weights) {
            // Загружаем подряд всё, что помещается, сохраняя исходный порядок грузов.
            if (load + weight > capacity) {
                usedDays++;
                if (usedDays > days) {
                    return false;
                }
                load = 0;
            }
            load += weight;
        }
        return true;
    }
}
