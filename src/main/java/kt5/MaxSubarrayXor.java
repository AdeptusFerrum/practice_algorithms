package kt5;
public class MaxSubarrayXor {
    public static int find(int[] nums) {
        // XOR подмассива равен XOR двух префиксов. Нулевой префикс стоит перед массивом.
        BinaryTrie prefixes = new BinaryTrie(1 + (nums.length + 1) * 30);
        prefixes.add(0);
        int prefix = 0;
        int maximum = 0;
        for (int number : nums) {
            prefix ^= number;
            maximum = Math.max(maximum, prefixes.maxXor(prefix));
            prefixes.add(prefix);
        }
        return maximum;
    }

    private static class BinaryTrie {
        // Индексы в двух массивах заменяют объекты узлов; 0 обозначает отсутствие потомка.
        private final int[] zero;
        private final int[] one;
        private int nextNode = 1;

        BinaryTrie(int capacity) {
            zero = new int[capacity];
            one = new int[capacity];
        }

        void add(int value) {
            int node = 0;
            for (int bit = 29; bit >= 0; bit--) {
                int[] children = (value & (1 << bit)) == 0 ? zero : one;
                if (children[node] == 0) {
                    children[node] = nextNode++;
                }
                node = children[node];
            }
        }

        int maxXor(int value) {
            int node = 0;
            int result = 0;
            for (int bit = 29; bit >= 0; bit--) {
                boolean set = (value & (1 << bit)) != 0;
                int opposite = set ? zero[node] : one[node];
                // Выгоднее сначала получить единицу в старшем разряде.
                if (opposite != 0) {
                    result |= 1 << bit;
                    node = opposite;
                } else {
                    node = set ? one[node] : zero[node];
                }
            }
            return result;
        }
    }
}
