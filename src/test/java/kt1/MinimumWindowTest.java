package kt1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumWindowTest {
    @Test
    void examples() {
        assertEquals("BANC", MinimumWindow.find("ADOBECODEBANC", "ABC"));
        assertEquals("", MinimumWindow.find("a", "aa"));
        assertEquals("AABBC", MinimumWindow.find("AAABBC", "AABC"));
    }

    @Test
    void respectsCaseAndDigits() {
        assertEquals("Aa1", MinimumWindow.find("Aa1A", "A1a"));
        assertEquals("", MinimumWindow.find("aaa", "A"));
    }

    @Test
    void keepsFirstWindowWhenLengthsAreEqual() {
        assertEquals("AB", MinimumWindow.find("ABBA", "AB"));
    }

    @Test
    void handlesExactMatchAndSingleCharacter() {
        assertEquals("ABC", MinimumWindow.find("ABC", "ABC"));
        assertEquals("b", MinimumWindow.find("abc", "b"));
    }

    @Test
    void missingCharacterOrEmptyTarget() {
        assertEquals("", MinimumWindow.find("ABC", "D"));
        assertEquals("", MinimumWindow.find("ABC", ""));
    }

    @Test
    void handlesMaximumLengthsAndRepeatedCharacters() {
        String target = "A".repeat(100_000);
        String source = "B".repeat(100_000) + target;
        assertEquals(target, MinimumWindow.find(source, target));
    }
}
