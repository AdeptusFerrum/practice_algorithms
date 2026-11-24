package kt6;
public class AlienAlphabet {
    public static String find(String[] words, int k) {
        boolean[][] before = new boolean[k][k];
        int[] indegree = new int[k];
        for (int i = 1; i < words.length; i++) {
            String first = words[i - 1];
            String second = words[i];
            int position = 0;
            int commonLength = Math.min(first.length(), second.length());
            while (position < commonLength && first.charAt(position) == second.charAt(position)) {
                position++;
            }
            if (position == commonLength) {
                if (first.length() > second.length()) {
                    return "IMPOSSIBLE";
                }
                continue;
            }

            // Порядок пары слов определяет только первая различающаяся буква.
            int from = first.charAt(position) - 'a';
            int to = second.charAt(position) - 'a';
            if (!before[from][to]) {
                before[from][to] = true;
                indegree[to]++;
            }
        }

        StringBuilder order = new StringBuilder();
        for (int position = 0; position < k; position++) {
            int letter = 0;
            // Букв не больше 26: выбираем наименьшую доступную обычным проходом.
            while (letter < k && indegree[letter] != 0) {
                letter++;
            }
            if (letter == k) {
                return "IMPOSSIBLE";
            }
            order.append((char) ('a' + letter));
            indegree[letter] = -1;
            for (int following = 0; following < k; following++) {
                if (before[letter][following]) {
                    indegree[following]--;
                }
            }
        }
        return order.toString();
    }
}
