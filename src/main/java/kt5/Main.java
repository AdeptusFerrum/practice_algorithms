package kt5;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            printExamples();
            return;
        }
        if (args.length != 1) {
            throw new IllegalArgumentException("Pass one task number from 1 to 6");
        }

        InputReader input = new InputReader();
        switch (Integer.parseInt(args[0])) {
            case 1 -> {
                for (String sequence : BalancedParentheses.generate(input.nextInt())) {
                    System.out.println(sequence);
                }
            }
            case 2 -> {
                int q = input.nextInt();
                PrefixDictionary dictionary = new PrefixDictionary();
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < q; i++) {
                    String operation = input.next();
                    String word = input.next();
                    switch (operation) {
                        case "ADD" -> dictionary.add(word);
                        case "REMOVE" -> dictionary.remove(word);
                        case "COUNT" -> output.append(dictionary.count(word)).append('\n');
                        default -> throw new IllegalArgumentException("Unknown operation: " + operation);
                    }
                }
                System.out.print(output);
            }
            case 3 -> {
                int[] numbers = TwoUniqueNumbers.find(input.readArray(input.nextInt()));
                System.out.println(numbers[0] + " " + numbers[1]);
            }
            case 4 -> {
                int n = input.nextInt();
                int b = input.nextInt();
                int[][] blocked = new int[b][2];
                for (int[] cell : blocked) {
                    cell[0] = input.nextInt();
                    cell[1] = input.nextInt();
                }
                System.out.println(BlockedQueens.count(n, blocked));
            }
            case 5 -> {
                int q = input.nextInt();
                KthWordDictionary dictionary = new KthWordDictionary();
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < q; i++) {
                    String operation = input.next();
                    String word = input.next();
                    switch (operation) {
                        case "ADD" -> dictionary.add(word);
                        case "REMOVE" -> dictionary.remove(word);
                        case "KTH" -> output.append(dictionary.find(word, input.nextInt())).append('\n');
                        default -> throw new IllegalArgumentException("Unknown operation: " + operation);
                    }
                }
                System.out.print(output);
            }
            case 6 -> System.out.println(MaxSubarrayXor.find(input.readArray(input.nextInt())));
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printExamples() {
        System.out.println("1. Balanced parentheses: " + BalancedParentheses.generate(3));
        PrefixDictionary prefixes = new PrefixDictionary();
        prefixes.add("app");
        prefixes.add("apple");
        prefixes.add("banana");
        prefixes.remove("app");
        System.out.println("2. Prefix count: " + prefixes.count("app"));
        System.out.println("3. Two unique numbers: "
                + Arrays.toString(TwoUniqueNumbers.find(new int[]{1, 2, 1, 3, 2, 5})));
        System.out.println("4. Blocked queens: " + BlockedQueens.count(4, new int[][]{{1, 2}}));
        KthWordDictionary words = new KthWordDictionary();
        words.add("app");
        words.add("apple");
        words.add("apply");
        System.out.println("5. Kth word: " + words.find("app", 2));
        System.out.println("6. Max subarray XOR: " + MaxSubarrayXor.find(new int[]{1, 2, 4}));
    }

    private static class InputReader {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokens = new StringTokenizer("");

        String next() throws IOException {
            while (!tokens.hasMoreTokens()) {
                String line = reader.readLine();
                if (line == null) {
                    throw new EOFException("Unexpected end of input");
                }
                tokens = new StringTokenizer(line);
            }
            return tokens.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        int[] readArray(int length) throws IOException {
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                values[i] = nextInt();
            }
            return values;
        }
    }
}
