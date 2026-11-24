package kt8;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortestCommonSupersequenceTest {
    @Test
    void choosesLexicographicallySmallestShortestString() {
        assertEquals("aba", ShortestCommonSupersequence.find("ab", "ba"));
        assertEquals("aba", ShortestCommonSupersequence.find("ba", "ab"));
    }

    @Test
    void sharesMatchingCharactersWhilePreservingOrder() {
        assertEquals("cabac", ShortestCommonSupersequence.find("abac", "cab"));
    }

    @Test
    void returnsContainingStringAndHandlesRepetitions() {
        assertEquals("banana", ShortestCommonSupersequence.find("banana", "ana"));
        assertEquals("aaaa", ShortestCommonSupersequence.find("aa", "aaaa"));
    }

    @Test
    void identicalStringsNeedNoExtraCharacters() {
        assertEquals("abcba", ShortestCommonSupersequence.find("abcba", "abcba"));
        assertEquals("a", ShortestCommonSupersequence.find("a", "a"));
    }

    @Test
    void prioritizesLengthOverLexicographicOrder() {
        assertEquals("ba", ShortestCommonSupersequence.find("a", "ba"));
        assertEquals("ba", ShortestCommonSupersequence.find("ba", "a"));
    }

    @Test
    void handlesDisjointStringsAndBothRemainingSuffixes() {
        assertEquals("aazz", ShortestCommonSupersequence.find("zz", "aa"));
        assertEquals("aazz", ShortestCommonSupersequence.find("aa", "zz"));
    }

    @Test
    void handlesMaximumLengths() {
        assertEquals("a".repeat(1_000) + "b".repeat(1_000),
                ShortestCommonSupersequence.find("a".repeat(1_000), "b".repeat(1_000)));
        assertEquals("ab".repeat(500) + "a",
                ShortestCommonSupersequence.find("ab".repeat(500), "ba".repeat(500)));
    }

    @Test
    void matchesEnumerationInLengthAndLexicographicOrder() {
        Random random = new Random(42);
        for (int test = 0; test < 80; test++) {
            String first = randomString(random);
            String second = randomString(random);
            String expected = null;
            for (int length = Math.max(first.length(), second.length());
                 length <= first.length() + second.length() && expected == null; length++) {
                expected = firstMatch(new char[length], 0, first, second);
            }
            assertEquals(expected, ShortestCommonSupersequence.find(first, second));
        }
    }

    private String firstMatch(char[] candidate, int index, String first, String second) {
        if (index == candidate.length) {
            String value = new String(candidate);
            return isSubsequence(first, value) && isSubsequence(second, value) ? value : null;
        }
        for (char letter = 'a'; letter <= 'c'; letter++) {
            candidate[index] = letter;
            String match = firstMatch(candidate, index + 1, first, second);
            if (match != null) {
                return match;
            }
        }
        return null;
    }

    private boolean isSubsequence(String subsequence, String value) {
        int index = 0;
        for (int i = 0; i < value.length() && index < subsequence.length(); i++) {
            if (value.charAt(i) == subsequence.charAt(index)) {
                index++;
            }
        }
        return index == subsequence.length();
    }

    private String randomString(Random random) {
        int length = 1 + random.nextInt(3);
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < length; i++) {
            value.append((char) ('a' + random.nextInt(3)));
        }
        return value.toString();
    }
}
