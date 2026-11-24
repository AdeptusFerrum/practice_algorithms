package kt2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipCapacityTest {
    @Test
    void findsMinimumCapacity() {
        assertEquals(15, ShipCapacity.minimum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5));
        assertEquals(6, ShipCapacity.minimum(new int[]{3, 2, 2, 4, 1, 4}, 3));
    }

    @Test
    void oneDayRequiresSumOfWeights() {
        assertEquals(12, ShipCapacity.minimum(new int[]{3, 4, 5}, 1));
    }

    @Test
    void oneDayPerCargoRequiresLargestWeight() {
        assertEquals(5, ShipCapacity.minimum(new int[]{3, 4, 5}, 3));
        assertEquals(7, ShipCapacity.minimum(new int[]{7}, 1));
    }

    @Test
    void preservesCargoOrder() {
        int[] weights = {8, 8, 1, 1};
        assertEquals(10, ShipCapacity.minimum(weights, 2));
        assertArrayEquals(new int[]{8, 8, 1, 1}, weights);
    }

    @Test
    void capacityCanExceedIntegerRange() {
        int[] weights = new int[200_000];
        Arrays.fill(weights, 1_000_000_000);
        assertEquals(200_000_000_000_000L, ShipCapacity.minimum(weights, 1));
    }
}
