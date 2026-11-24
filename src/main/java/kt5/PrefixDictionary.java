package kt5;
public class PrefixDictionary {
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
    }

    public void remove(String word) {
        Node node = root;
        node.wordCount--;
        for (int i = 0; i < word.length(); i++) {
            int letter = word.charAt(i) - 'a';
            Node child = node.children[letter];
            child.wordCount--;
            // Пустая ветвь больше не нужна; оставшуюся часть слова обходить не требуется.
            if (child.wordCount == 0) {
                node.children[letter] = null;
                return;
            }
            node = child;
        }
    }

    public int count(String prefix) {
        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            node = node.children[prefix.charAt(i) - 'a'];
            if (node == null) {
                return 0;
            }
        }
        return node.wordCount;
    }

    private static class Node {
        private final Node[] children = new Node[26];
        // Все слова в поддереве начинаются с префикса, ведущего к этому узлу.
        private int wordCount;
    }
}
