package kt9;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromePartitionTest {
    @Test
    void countsPartsRatherThanCuts() {
        assertEquals(2, PalindromePartition.minParts("aab"));
        assertEquals(1, PalindromePartition.minParts("a"));
    }

    @Test
    void recognizesOddAndEvenPalindromes() {
        assertEquals(1, PalindromePartition.minParts("racecar"));
        assertEquals(1, PalindromePartition.minParts("abba"));
        assertEquals(1, PalindromePartition.minParts("aaaa"));
    }

    @Test
    void handlesStringsWithoutLongerPalindromes() {
        assertEquals(6, PalindromePartition.minParts("abcdef"));
    }

    @Test
    void choosesBestPartitionAmongOverlappingPalindromes() {
        assertEquals(4, PalindromePartition.minParts("ababbbabbababa"));
        assertEquals(2, PalindromePartition.minParts("abacdc"));
    }

    @Test
    void handlesMaximumLength() {
        assertEquals(1, PalindromePartition.minParts("a".repeat(4_000)));
        assertEquals(2, PalindromePartition.minParts("ab".repeat(2_000)));
    }

    @Test
    void matchesEnumerationOfAllPartitions() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int n = 1 + random.nextInt(12);
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < n; i++) {
                value.append((char) ('a' + random.nextInt(3)));
            }
            String text = value.toString();
            assertEquals(enumerate(text, 0), PalindromePartition.minParts(text));
        }
    }

    private int enumerate(String value, int start) {
        if (start == value.length()) {
            return 0;
        }
        int best = value.length();
        for (int end = start + 1; end <= value.length(); end++) {
            String part = value.substring(start, end);
            if (part.contentEquals(new StringBuilder(part).reverse())) {
                best = Math.min(best, 1 + enumerate(value, end));
            }
        }
        return best;
    }
}
