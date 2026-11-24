package kt5;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthWordDictionaryTest {
    @Test
    void returnsNoneForMissingPrefixOrLargeK() {
        KthWordDictionary dictionary = new KthWordDictionary();
        assertEquals("NONE", dictionary.find("a", 1));
        dictionary.add("apple");
        assertEquals("NONE", dictionary.find("b", 1));
        assertEquals("NONE", dictionary.find("apples", 1));
        assertEquals("NONE", dictionary.find("a", 200_000));
    }

    @Test
    void shorterWordPrecedesItsExtensions() {
        KthWordDictionary dictionary = new KthWordDictionary();
        dictionary.add("apply");
        dictionary.add("apple");
        dictionary.add("app");
        assertEquals("app", dictionary.find("app", 1));
        assertEquals("apple", dictionary.find("app", 2));
        assertEquals("apply", dictionary.find("app", 3));
        assertEquals("NONE", dictionary.find("app", 4));
    }

    @Test
    void followsLetterOrderAcrossBranches() {
        KthWordDictionary dictionary = new KthWordDictionary();
        for (String word : List.of("az", "abz", "aba", "aa", "b")) {
            dictionary.add(word);
        }
        assertEquals("aa", dictionary.find("a", 1));
        assertEquals("aba", dictionary.find("a", 2));
        assertEquals("abz", dictionary.find("a", 3));
        assertEquals("az", dictionary.find("a", 4));
    }

    @Test
    void removingPrefixWordKeepsItsExtensions() {
        KthWordDictionary dictionary = new KthWordDictionary();
        dictionary.add("a");
        dictionary.add("ab");
        dictionary.add("abc");
        dictionary.remove("ab");
        assertEquals("abc", dictionary.find("ab", 1));
        assertEquals("abc", dictionary.find("a", 2));
        dictionary.remove("a");
        assertEquals("abc", dictionary.find("a", 1));
        assertEquals("NONE", dictionary.find("a", 2));
        dictionary.add("ab");
        assertEquals("ab", dictionary.find("a", 1));
    }

    @Test
    void removingLeafKeepsShorterWordAndOtherBranch() {
        KthWordDictionary dictionary = new KthWordDictionary();
        dictionary.add("app");
        dictionary.add("apple");
        dictionary.add("apply");
        dictionary.remove("apple");
        assertEquals("app", dictionary.find("app", 1));
        assertEquals("apply", dictionary.find("app", 2));
        assertEquals("NONE", dictionary.find("apple", 1));
    }

    @Test
    void canReuseRemovedBranchAtMaximumWordLength() {
        KthWordDictionary dictionary = new KthWordDictionary();
        String word = "z".repeat(50);
        dictionary.add(word);
        assertEquals(word, dictionary.find("z", 1));
        dictionary.remove(word);
        assertEquals("NONE", dictionary.find("z", 1));
        dictionary.add(word);
        assertEquals(word, dictionary.find(word, 1));
    }

    @Test
    void matchesSortedSetAfterAddsAndRemovals() {
        KthWordDictionary dictionary = new KthWordDictionary();
        TreeSet<String> words = new TreeSet<>();
        Random random = new Random(42);
        for (int operation = 0; operation < 500; operation++) {
            StringBuilder word = new StringBuilder();
            int length = 1 + random.nextInt(5);
            for (int i = 0; i < length; i++) {
                word.append((char) ('a' + random.nextInt(3)));
            }
            String value = word.toString();
            if (words.remove(value)) {
                dictionary.remove(value);
            } else {
                words.add(value);
                dictionary.add(value);
            }
            String prefix = value.substring(0, 1 + random.nextInt(length));
            List<String> expected = words.stream().filter(s -> s.startsWith(prefix)).toList();
            for (int k = 1; k <= expected.size(); k++) {
                assertEquals(expected.get(k - 1), dictionary.find(prefix, k));
            }
            assertEquals("NONE", dictionary.find(prefix, expected.size() + 1));
        }
    }

    @Test
    void handlesManyWordsWithSamePrefix() {
        KthWordDictionary dictionary = new KthWordDictionary();
        for (char first = 'a'; first <= 'z'; first++) {
            for (char second = 'a'; second <= 'z'; second++) {
                for (char third = 'a'; third <= 'z'; third++) {
                    dictionary.add("x" + first + second + third);
                }
            }
        }
        assertEquals("xaaa", dictionary.find("x", 1));
        assertEquals("xzzz", dictionary.find("x", 17_576));
        dictionary.remove("xaaa");
        dictionary.remove("xzzz");
        assertEquals("xaab", dictionary.find("x", 1));
        assertEquals("xzzy", dictionary.find("x", 17_574));
        assertEquals("NONE", dictionary.find("x", 17_575));
    }

    @Test
    void instancesAreIndependent() {
        KthWordDictionary first = new KthWordDictionary();
        KthWordDictionary second = new KthWordDictionary();
        first.add("a");
        assertEquals("NONE", second.find("a", 1));
    }
}
