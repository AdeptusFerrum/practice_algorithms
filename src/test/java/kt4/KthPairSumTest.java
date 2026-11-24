package kt4;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KthPairSumTest {
    @Test
    void findsKthSum() {
        assertEquals(9, KthPairSum.find(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 4));
    }

    @Test
    void countsEqualSumsFromDifferentPairs() {
        int[] a = {1, 1, 2};
        int[] b = {1, 2};
        assertEquals(2, KthPairSum.find(a, b, 2));
        assertEquals(3, KthPairSum.find(a, b, 5));
        assertEquals(4, KthPairSum.find(a, b, 6));
    }

    @Test
    void handlesNegativeSumsAndBoundaryPositions() {
        int[] a = {-5, -1, 2};
        int[] b = {-3, 4};
        assertEquals(-8, KthPairSum.find(a, b, 1));
        assertEquals(-4, KthPairSum.find(a, b, 2));
        assertEquals(6, KthPairSum.find(a, b, 6));
    }

    @Test
    void handlesSingleElementInEitherArray() {
        assertEquals(6, KthPairSum.find(new int[]{1, 4, 8}, new int[]{2}, 2));
        assertEquals(6, KthPairSum.find(new int[]{2}, new int[]{1, 4, 8}, 2));
        assertEquals(-2_000_000_000L,
                KthPairSum.find(new int[]{-1_000_000_000}, new int[]{-1_000_000_000}, 1));
    }

    @Test
    void matchesSortedPairSumsAtEveryPosition() {
        int[] a = {-4, -4, 0, 3};
        int[] b = {-2, 1, 1, 5, 9};
        long[] sums = new long[a.length * b.length];
        int index = 0;
        for (int first : a) {
            for (int second : b) {
                sums[index++] = (long) first + second;
            }
        }
        Arrays.sort(sums);
        for (int k = 1; k <= sums.length; k++) {
            assertEquals(sums[k - 1], KthPairSum.find(a, b, k), "k = " + k);
        }
        assertArrayEquals(new int[]{-4, -4, 0, 3}, a);
        assertArrayEquals(new int[]{-2, 1, 1, 5, 9}, b);
    }

    @Test
    void handlesMaximumArraysWithoutBuildingAllPairs() {
        int[] a = new int[200_000];
        int[] b = new int[200_000];
        Arrays.fill(a, 1_000_000_000);
        Arrays.fill(b, 1_000_000_000);
        assertEquals(2_000_000_000L, KthPairSum.find(a, b, 200_000));
    }
}
