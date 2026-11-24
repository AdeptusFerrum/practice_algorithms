package kt1;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxWaterContainerTest {
    @Test
    void examples() {
        assertEquals(49, MaxWaterContainer.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        assertEquals(1, MaxWaterContainer.maxArea(new int[]{1, 1}));
    }

    @Test
    void zeroHeights() {
        assertEquals(0, MaxWaterContainer.maxArea(new int[]{0, 0, 0}));
        assertEquals(0, MaxWaterContainer.maxArea(new int[]{0, 10}));
    }

    @Test
    void handlesIncreasingAndDecreasingHeights() {
        assertEquals(6, MaxWaterContainer.maxArea(new int[]{1, 2, 3, 4, 5}));
        assertEquals(6, MaxWaterContainer.maxArea(new int[]{5, 4, 3, 2, 1}));
    }

    @Test
    void bestWallsCanBeInsideArray() {
        assertEquals(20, MaxWaterContainer.maxArea(new int[]{1, 10, 1, 10, 1}));
    }

    @Test
    void areaCanExceedIntegerRange() {
        int[] height = new int[200_000];
        Arrays.fill(height, 1_000_000_000);
        assertEquals(199_999_000_000_000L, MaxWaterContainer.maxArea(height));
    }
}
