package kt5;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TwoUniqueNumbersTest {
    @Test
    void findsTwoUniqueNumbers() {
        assertArrayEquals(new int[]{3, 5}, TwoUniqueNumbers.find(new int[]{1, 2, 1, 3, 2, 5}));
    }

    @Test
    void returnsNumbersInAscendingOrder() {
        assertArrayEquals(new int[]{3, 8}, TwoUniqueNumbers.find(new int[]{8, 3}));
    }

    @Test
    void zeroCanBeUnique() {
        assertArrayEquals(new int[]{0, 7}, TwoUniqueNumbers.find(new int[]{5, 0, 7, 5}));
    }

    @Test
    void distinguishingBitCanBeHigh() {
        assertArrayEquals(new int[]{0, 1 << 29},
                TwoUniqueNumbers.find(new int[]{1, 1 << 29, 0, 1}));
    }

    @Test
    void handlesValueLimitsWithoutChangingInput() {
        int[] nums = {1_000_000_000, 0, 3, 0};
        assertArrayEquals(new int[]{3, 1_000_000_000}, TwoUniqueNumbers.find(nums));
        assertArrayEquals(new int[]{1_000_000_000, 0, 3, 0}, nums);
    }

    @Test
    void handlesMaximumLength() {
        int[] nums = new int[200_000];
        for (int i = 0; i < 99_999; i++) {
            nums[2 * i] = i;
            nums[2 * i + 1] = i;
        }
        nums[199_998] = 1_000_000_000;
        nums[199_999] = 999_999_999;
        assertArrayEquals(new int[]{999_999_999, 1_000_000_000}, TwoUniqueNumbers.find(nums));
    }
}
