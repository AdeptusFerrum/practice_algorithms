package kt8;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IncreasingSubsequencesTest {
    @Test
    void countsDifferentLongestSubsequences() {
        int[] values = {1, 3, 5, 4, 7};
        assertEquals(new IncreasingSubsequences.Result(4, 2), IncreasingSubsequences.find(values));
        assertArrayEquals(new int[]{1, 3, 5, 4, 7}, values);
    }

    @Test
    void equalValuesDoNotExtendEachOther() {
        assertEquals(new IncreasingSubsequences.Result(1, 5),
                IncreasingSubsequences.find(new int[]{2, 2, 2, 2, 2}));
        assertEquals(new IncreasingSubsequences.Result(1, 1), IncreasingSubsequences.find(new int[]{0}));
    }

    @Test
    void countsIndexSequencesWithEqualValuesSeparately() {
        assertEquals(new IncreasingSubsequences.Result(3, 8),
                IncreasingSubsequences.find(new int[]{1, 1, 2, 2, 3, 3}));
        assertEquals(new IncreasingSubsequences.Result(2, 6),
                IncreasingSubsequences.find(new int[]{1, 2, 1, 2, 1, 2}));
    }

    @Test
    void shorterPrefixesDoNotContributeToLongestCount() {
        assertEquals(new IncreasingSubsequences.Result(3, 1),
                IncreasingSubsequences.find(new int[]{1, 2, 0, 3}));
    }

    @Test
    void compressesNegativeAndLargeValues() {
        assertEquals(new IncreasingSubsequences.Result(3, 2),
                IncreasingSubsequences.find(new int[]{-1_000_000_000, 0, 1_000_000_000, -1_000_000_000, 1_000_000_000}));
    }

    @Test
    void handlesMaximumIncreasingAndDecreasingArrays() {
        int[] values = new int[200_000];
        for (int i = 0; i < values.length; i++) {
            values[i] = i;
        }
        assertEquals(new IncreasingSubsequences.Result(values.length, 1), IncreasingSubsequences.find(values));
        for (int i = 0; i < values.length; i++) {
            values[i] = -i;
        }
        assertEquals(new IncreasingSubsequences.Result(1, values.length), IncreasingSubsequences.find(values));
    }

    @Test
    void countsModuloPrimeAtMaximumSize() {
        int[] values = new int[200_000];
        for (int i = 0; i < values.length; i++) {
            values[i] = i / 100;
        }
        int expected = BigInteger.valueOf(100)
                .modPow(BigInteger.valueOf(2_000), BigInteger.valueOf(1_000_000_007)).intValue();
        assertEquals(new IncreasingSubsequences.Result(2_000, expected), IncreasingSubsequences.find(values));
    }

    @Test
    void matchesEnumerationOfAllIndexSubsets() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int[] values = new int[1 + random.nextInt(12)];
            for (int i = 0; i < values.length; i++) {
                values[i] = random.nextInt(7) - 3;
            }
            int length = 0;
            int count = 0;
            for (int mask = 1; mask < 1 << values.length; mask++) {
                int previous = Integer.MIN_VALUE;
                int currentLength = 0;
                boolean increasing = true;
                for (int i = 0; i < values.length; i++) {
                    if ((mask & (1 << i)) != 0) {
                        if (values[i] <= previous) {
                            increasing = false;
                            break;
                        }
                        previous = values[i];
                        currentLength++;
                    }
                }
                if (increasing && currentLength > length) {
                    length = currentLength;
                    count = 1;
                } else if (increasing && currentLength == length) {
                    count++;
                }
            }
            assertEquals(new IncreasingSubsequences.Result(length, count), IncreasingSubsequences.find(values));
        }
    }
}
