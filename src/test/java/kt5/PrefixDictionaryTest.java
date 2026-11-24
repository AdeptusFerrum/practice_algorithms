package kt5;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrefixDictionaryTest {
    @Test
    void emptyDictionaryAndMissingPrefix() {
        PrefixDictionary dictionary = new PrefixDictionary();
        assertEquals(0, dictionary.count("a"));
        dictionary.add("apple");
        assertEquals(0, dictionary.count("b"));
        assertEquals(0, dictionary.count("apples"));
    }

    @Test
    void countsWordsWithSharedPrefix() {
        PrefixDictionary dictionary = new PrefixDictionary();
        dictionary.add("app");
        dictionary.add("apple");
        dictionary.add("apply");
        dictionary.add("banana");
        assertEquals(3, dictionary.count("app"));
        assertEquals(2, dictionary.count("appl"));
        assertEquals(1, dictionary.count("apple"));
        assertEquals(1, dictionary.count("b"));
    }

    @Test
    void removesWordButKeepsItsExtensions() {
        PrefixDictionary dictionary = new PrefixDictionary();
        dictionary.add("a");
        dictionary.add("ab");
        dictionary.add("abc");
        dictionary.remove("ab");
        assertEquals(2, dictionary.count("a"));
        assertEquals(1, dictionary.count("ab"));
        dictionary.remove("a");
        assertEquals(1, dictionary.count("a"));
        assertEquals(1, dictionary.count("abc"));
    }

    @Test
    void removesLeafButKeepsShorterWordAndOtherBranch() {
        PrefixDictionary dictionary = new PrefixDictionary();
        dictionary.add("app");
        dictionary.add("apple");
        dictionary.add("apply");
        dictionary.remove("apple");
        assertEquals(2, dictionary.count("app"));
        assertEquals(0, dictionary.count("apple"));
        assertEquals(1, dictionary.count("apply"));
    }

    @Test
    void canAddWordAgainAfterRemovingWholeBranch() {
        PrefixDictionary dictionary = new PrefixDictionary();
        dictionary.add("zebra");
        dictionary.remove("zebra");
        assertEquals(0, dictionary.count("z"));
        dictionary.add("zebra");
        assertEquals(1, dictionary.count("z"));
    }

    @Test
    void instancesAreIndependent() {
        PrefixDictionary first = new PrefixDictionary();
        PrefixDictionary second = new PrefixDictionary();
        first.add("a");
        assertEquals(0, second.count("a"));
    }

    @Test
    void handlesManyWordsOfMaximumLength() {
        PrefixDictionary dictionary = new PrefixDictionary();
        for (int i = 0; i < 10_000; i++) {
            int value = i;
            StringBuilder word = new StringBuilder("a");
            for (int letter = 0; letter < 3; letter++) {
                word.append((char) ('a' + value % 26));
                value /= 26;
            }
            word.append("z".repeat(46));
            dictionary.add(word.toString());
        }
        assertEquals(10_000, dictionary.count("a"));
        assertEquals(1, dictionary.count("aaaa" + "z".repeat(46)));
        dictionary.remove("aaaa" + "z".repeat(46));
        assertEquals(9_999, dictionary.count("a"));
    }
}
