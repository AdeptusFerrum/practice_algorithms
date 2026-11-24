package kt2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchInsertPositionTest {
    @Test
    void findsExistingNumberAndInsertionPosition() {
        assertEquals(2, SearchInsertPosition.find(new int[]{1, 3, 5, 6}, 5));
        assertEquals(1, SearchInsertPosition.find(new int[]{1, 3, 5, 6}, 2));
    }

    @Test
    void returnsFirstOccurrence() {
        assertEquals(1, SearchInsertPosition.find(new int[]{1, 3, 3, 3, 5}, 3));
    }

    @Test
    void emptyArray() {
        assertEquals(0, SearchInsertPosition.find(new int[]{}, 7));
    }

    @Test
    void insertsAtBothEnds() {
        assertEquals(0, SearchInsertPosition.find(new int[]{-5, 0, 5}, -1_000_000_000));
        assertEquals(3, SearchInsertPosition.find(new int[]{-5, 0, 5}, 1_000_000_000));
    }

    @Test
    void singleElement() {
        assertEquals(0, SearchInsertPosition.find(new int[]{4}, 4));
        assertEquals(1, SearchInsertPosition.find(new int[]{4}, 5));
    }

    @Test
    void handlesMaximumLengthWithDuplicates() {
        int[] nums = new int[200_000];
        Arrays.fill(nums, 1_000_000_000);
        assertEquals(0, SearchInsertPosition.find(nums, 1_000_000_000));
    }
}
