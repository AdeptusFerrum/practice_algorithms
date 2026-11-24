package kt4;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TopKFrequentTest {
    @Test
    void ordersByFrequency() {
        assertArrayEquals(new int[]{1, 2}, TopKFrequent.find(new int[]{1, 1, 1, 2, 2, 3}, 2));
    }

    @Test
    void ordersTiesByNumber() {
        assertArrayEquals(new int[]{-1_000_000_000, 0, 1_000_000_000},
                TopKFrequent.find(new int[]{1_000_000_000, 0, -1_000_000_000}, 3));
    }

    @Test
    void choosesSmallerNumbersAtTieBoundary() {
        assertArrayEquals(new int[]{7, -2, 1},
                TopKFrequent.find(new int[]{4, 7, 1, 4, -2, 7, 1, -2, 7}, 3));
    }

    @Test
    void returnsSingleMostFrequentNumber() {
        assertArrayEquals(new int[]{8}, TopKFrequent.find(new int[]{2, 8, 8, 3}, 1));
    }

    @Test
    void allNumbersAreEqual() {
        assertArrayEquals(new int[]{-5}, TopKFrequent.find(new int[]{-5, -5, -5}, 1));
    }

    @Test
    void doesNotChangeInput() {
        int[] nums = {3, 1, 3, 2};
        assertArrayEquals(new int[]{3, 1, 2}, TopKFrequent.find(nums, 3));
        assertArrayEquals(new int[]{3, 1, 3, 2}, nums);
    }

    @Test
    void handlesMaximumDistinctCount() {
        int[] nums = IntStream.range(0, 200_000).toArray();
        assertArrayEquals(new int[]{0, 1, 2}, TopKFrequent.find(nums, 3));
    }
}
