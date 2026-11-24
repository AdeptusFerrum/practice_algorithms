package kt1;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TwoTypesWindowTest {
    @Test
    void examples() {
        assertEquals(3, TwoTypesWindow.maxLength(new int[]{1, 2, 1}));
        assertEquals(3, TwoTypesWindow.maxLength(new int[]{0, 1, 2, 2}));
        assertEquals(4, TwoTypesWindow.maxLength(new int[]{1, 2, 3, 2, 2}));
    }

    @Test
    void emptyArray() {
        assertEquals(0, TwoTypesWindow.maxLength(new int[]{}));
    }

    @Test
    void supportsOneTypeAndSingleItem() {
        assertEquals(1, TwoTypesWindow.maxLength(new int[]{1_000_000}));
        assertEquals(4, TwoTypesWindow.maxLength(new int[]{0, 0, 0, 0}));
    }

    @Test
    void removesAllOccurrencesOfOldTypeBeforeExpanding() {
        assertEquals(5, TwoTypesWindow.maxLength(new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}));
    }

    @Test
    void handlesMaximumLengthWithAlternatingTypes() {
        int[] items = IntStream.range(0, 200_000).map(i -> i % 2).toArray();
        assertEquals(200_000, TwoTypesWindow.maxLength(items));
    }
}
