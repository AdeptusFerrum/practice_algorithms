package kt1;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeSumTest {
    @Test
    void examples() {
        assertEquals(List.of(List.of(-1, -1, 2), List.of(-1, 0, 1)),
                ThreeSum.find(new int[]{-1, 0, 1, 2, -1, -4}));
        assertEquals(List.of(List.of(0, 0, 0)), ThreeSum.find(new int[]{0, 0, 0, 0}));
        assertEquals(List.of(), ThreeSum.find(new int[]{1, 2, -2, -1}));
    }

    @Test
    void skipsRepeatedPairs() {
        assertEquals(List.of(List.of(-2, 0, 2)),
                ThreeSum.find(new int[]{-2, -2, 0, 0, 2, 2}));
    }

    @Test
    void doesNotReuseAnIndex() {
        assertEquals(List.of(), ThreeSum.find(new int[]{-2, 1, 3}));
    }

    @Test
    void handlesValueLimitsWithoutChangingInput() {
        int[] nums = {100_000, -100_000, 0};
        assertEquals(List.of(List.of(-100_000, 0, 100_000)), ThreeSum.find(nums));
        assertArrayEquals(new int[]{100_000, -100_000, 0}, nums);
    }

    @Test
    void handlesMaximumLengthWithDuplicates() {
        assertEquals(List.of(List.of(0, 0, 0)), ThreeSum.find(new int[3_000]));
    }
}
