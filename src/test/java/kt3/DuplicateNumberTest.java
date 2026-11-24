package kt3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DuplicateNumberTest {
    @Test
    void findsRepeatedValue() {
        assertEquals(2, DuplicateNumber.find(new int[]{1, 3, 4, 2, 2}));
        assertEquals(3, DuplicateNumber.find(new int[]{3, 1, 3, 4, 2}));
    }

    @Test
    void smallestArray() {
        assertEquals(1, DuplicateNumber.find(new int[]{1, 1}));
    }

    @Test
    void valueCanAppearMoreThanTwice() {
        assertEquals(2, DuplicateNumber.find(new int[]{2, 2, 2, 2, 2}));
    }

    @Test
    void duplicateCanBeSmallestOrLargestValue() {
        assertEquals(1, DuplicateNumber.find(new int[]{1, 1, 2, 3}));
        assertEquals(3, DuplicateNumber.find(new int[]{3, 3, 1, 2}));
    }

    @Test
    void doesNotChangeInput() {
        int[] nums = {2, 1, 3, 2, 4};
        assertEquals(2, DuplicateNumber.find(nums));
        assertArrayEquals(new int[]{2, 1, 3, 2, 4}, nums);
    }

    @Test
    void handlesMaximumLength() {
        int n = 1_000_000;
        int[] nums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }
        nums[n] = n;
        assertEquals(n, DuplicateNumber.find(nums));
    }
}
