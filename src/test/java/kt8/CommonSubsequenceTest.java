package kt8;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonSubsequenceTest {
    @Test
    void allowsDeletingCharactersBetweenMatches() {
        assertEquals(3, CommonSubsequence.length("abcde", "ace"));
        assertEquals(3, CommonSubsequence.length("ace", "abcde"));
    }

    @Test
    void preservesOrderAndHandlesRepeatedLetters() {
        assertEquals(2, CommonSubsequence.length("aba", "baa"));
        assertEquals(2, CommonSubsequence.length("aaaa", "aa"));
    }

    @Test
    void disjointStringsHaveNoCommonSubsequence() {
        assertEquals(0, CommonSubsequence.length("abc", "xyz"));
    }

    @Test
    void handlesIdenticalStringsAndSingleCharacters() {
        assertEquals(5, CommonSubsequence.length("abcba", "abcba"));
        assertEquals(1, CommonSubsequence.length("z", "z"));
        assertEquals(0, CommonSubsequence.length("z", "a"));
    }

    @Test
    void handlesMaximumLengths() {
        String first = "a".repeat(1_500) + "b".repeat(1_500);
        String second = "b".repeat(1_500) + "a".repeat(1_500);
        assertEquals(1_500, CommonSubsequence.length(first, second));
    }

    @Test
    void matchesEnumerationOfSubsequences() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            String first = randomString(random);
            String second = randomString(random);
            int best = 0;
            for (int mask = 0; mask < 1 << first.length(); mask++) {
                StringBuilder candidate = new StringBuilder();
                for (int i = 0; i < first.length(); i++) {
                    if ((mask & (1 << i)) != 0) {
                        candidate.append(first.charAt(i));
                    }
                }
                int matched = 0;
                for (int i = 0; i < second.length() && matched < candidate.length(); i++) {
                    if (second.charAt(i) == candidate.charAt(matched)) {
                        matched++;
                    }
                }
                if (matched == candidate.length()) {
                    best = Math.max(best, matched);
                }
            }
            assertEquals(best, CommonSubsequence.length(first, second));
        }
    }

    private String randomString(Random random) {
        int length = 1 + random.nextInt(9);
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < length; i++) {
            value.append((char) ('a' + random.nextInt(3)));
        }
        return value.toString();
    }
}
