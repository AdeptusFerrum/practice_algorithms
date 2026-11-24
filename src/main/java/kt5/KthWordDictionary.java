package kt5;
public class KthWordDictionary {
    private final Node root = new Node();

    public void add(String word) {
        Node node = root;
        node.wordCount++;
        for (int i = 0; i < word.length(); i++) {
            int letter = word.charAt(i) - 'a';
            if (node.children[letter] == null) {
                node.children[letter] = new Node();
            }
            node = node.children[letter];
            node.wordCount++;
        }
        node.wordEnd = true;
    }

    public void remove(String word) {
        Node node = root;
        node.wordCount--;
        for (int i = 0; i < word.length(); i++) {
            int letter = word.charAt(i) - 'a';
            Node child = node.children[letter];
            child.wordCount--;
            if (child.wordCount == 0) {
                node.children[letter] = null;
                return;
            }
            node = child;
        }
        node.wordEnd = false;
    }

    public String find(String prefix, int k) {
        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            node = node.children[prefix.charAt(i) - 'a'];
            if (node == null) {
                return "NONE";
            }
        }
        if (node.wordCount < k) {
            return "NONE";
        }

        StringBuilder word = new StringBuilder(prefix);
        while (true) {
            // Само слово предшествует всем своим продолжениям.
            if (node.wordEnd) {
                if (k == 1) {
                    return word.toString();
                }
                k--;
            }
            for (int letter = 0; letter < 26; letter++) {
                Node child = node.children[letter];
                if (child == null) {
                    continue;
                }
                // Пропускаем целые поддеревья, не перебирая слова внутри них.
                if (k > child.wordCount) {
                    k -= child.wordCount;
                } else {
                    word.append((char) ('a' + letter));
                    node = child;
                    break;
                }
            }
        }
    }

    private static class Node {
        private final Node[] children = new Node[26];
        private int wordCount;
        private boolean wordEnd;
    }
}
