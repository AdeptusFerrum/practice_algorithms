package kt6;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AlienAlphabetTest {
    @Test
    void reconstructsOrderWithoutChangingWords() {
        String[] words = {"ba", "bc", "ac"};
        assertEquals("bac", AlienAlphabet.find(words, 3));
        assertArrayEquals(new String[]{"ba", "bc", "ac"}, words);
    }

    @Test
    void includesUnusedLettersAndChoosesSmallestAvailableLetter() {
        assertEquals("bacd", AlienAlphabet.find(new String[]{"b", "a"}, 4));
    }

    @Test
    void oneWordDoesNotRestrictAlphabet() {
        assertEquals("abcdefghijklmnopqrstuvwxyz", AlienAlphabet.find(new String[]{"zyx"}, 26));
        assertEquals("a", AlienAlphabet.find(new String[]{"a"}, 1));
    }

    @Test
    void onlyFirstDifferentLetterConstrainsOrder() {
        assertEquals("ab", AlienAlphabet.find(new String[]{"ab", "ba"}, 2));
    }

    @Test
    void longerWordBeforeItsPrefixIsImpossible() {
        assertEquals("IMPOSSIBLE", AlienAlphabet.find(new String[]{"abc", "ab"}, 3));
    }

    @Test
    void prefixBeforeLongerWordAndEqualWordsAreAllowed() {
        assertEquals("abc", AlienAlphabet.find(new String[]{"a", "a", "ab", "abc"}, 3));
    }

    @Test
    void repeatedEdgesAreCountedOnce() {
        assertEquals("ab", AlienAlphabet.find(new String[]{"aa", "ab", "ba", "bb"}, 2));
    }

    @Test
    void cycleIsImpossible() {
        assertEquals("IMPOSSIBLE", AlienAlphabet.find(new String[]{"a", "b", "a"}, 2));
    }

    @Test
    void handlesMaximumWordCountAndTotalLength() {
        String[] words = new String[100_000];
        Arrays.fill(words, "aa");
        assertEquals("abcdefghijklmnopqrstuvwxyz", AlienAlphabet.find(words, 26));
    }

    @Test
    void handlesLongCommonPrefix() {
        String prefix = "a".repeat(99_999);
        assertEquals("abc", AlienAlphabet.find(new String[]{prefix + "b", prefix + "c"}, 3));
    }
}
