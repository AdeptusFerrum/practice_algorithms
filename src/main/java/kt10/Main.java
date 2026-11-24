package kt10;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;

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
        PrintWriter output = new PrintWriter(System.out);
        switch (Integer.parseInt(args[0])) {
            case 1 -> output.println(IntervalScheduling.maxCount(input.readPairs(input.nextInt())));
            case 2 -> {
                int q = input.nextInt();
                MinMaxStack stack = new MinMaxStack(q);
                for (int i = 0; i < q; i++) {
                    switch (input.next()) {
                        case "PUSH" -> stack.push(input.nextInt());
                        case "POP" -> stack.pop();
                        case "MIN" -> output.println(stack.min());
                        case "MAX" -> output.println(stack.max());
                        default -> throw new IllegalArgumentException("Unknown stack command");
                    }
                }
            }
            case 3 -> {
                int n = input.nextInt();
                int k = input.nextInt();
                output.println(KthElement.find(input.readArray(n), k));
            }
            case 4 -> output.println(JobScheduling.maxProfit(input.readPairs(input.nextInt())));
            case 5 -> {
                int capacity = input.nextInt();
                int q = input.nextInt();
                LfuCache cache = new LfuCache(capacity);
                for (int i = 0; i < q; i++) {
                    switch (input.next()) {
                        case "GET" -> output.println(cache.get(input.nextInt()));
                        case "PUT" -> cache.put(input.nextInt(), input.nextInt());
                        default -> throw new IllegalArgumentException("Unknown cache command");
                    }
                }
            }
            case 6 -> {
                int n = input.nextInt();
                int k = input.nextInt();
                NearestPoints.Point[] points = new NearestPoints.Point[n];
                for (int i = 0; i < n; i++) {
                    points[i] = new NearestPoints.Point(input.nextInt(), input.nextInt(), i + 1);
                }
                for (NearestPoints.Point point : NearestPoints.find(points, k)) {
                    output.println(point.x() + " " + point.y() + " " + point.index());
                }
            }
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
        output.flush();
    }

    private static void printExamples() {
        System.out.println("1. Interval scheduling: "
                + IntervalScheduling.maxCount(new int[][]{{1, 3}, {2, 4}, {3, 5}, {0, 7}}));
        MinMaxStack stack = new MinMaxStack(3);
        stack.push(3);
        stack.push(-2);
        stack.push(7);
        System.out.println("2. Min/max stack: " + stack.min() + " " + stack.max());
        System.out.println("3. Kth element: " + KthElement.find(new int[]{7, 1, 5, 3, 3}, 3));
        System.out.println("4. Job scheduling: " + JobScheduling.maxProfit(new int[][]{{1, 20}, {2, 50}, {1, 40}}));
        LfuCache cache = new LfuCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        cache.get(1);
        cache.put(3, 30);
        System.out.println("5. LFU cache: " + cache.get(2) + " " + cache.get(3));
        NearestPoints.Point[] points = {
                new NearestPoints.Point(1, 1, 1), new NearestPoints.Point(-1, 0, 2),
                new NearestPoints.Point(0, 1, 3), new NearestPoints.Point(3, 0, 4)
        };
        System.out.print("6. Nearest points:");
        for (NearestPoints.Point point : NearestPoints.find(points, 2)) {
            System.out.print(" (" + point.x() + ", " + point.y() + ", " + point.index() + ")");
        }
        System.out.println();
    }

    private static class InputReader {
        // Буфер не зависит от длины строки с миллионами чисел.
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        private int firstCharacter() throws IOException {
            int character;
            do {
                character = input.read();
            } while (character != -1 && character <= ' ');
            if (character == -1) {
                throw new EOFException("Unexpected end of input");
            }
            return character;
        }

        String next() throws IOException {
            StringBuilder value = new StringBuilder();
            int character = firstCharacter();
            while (character > ' ') {
                value.append((char) character);
                character = input.read();
            }
            return value.toString();
        }

        int nextInt() throws IOException {
            int character = firstCharacter();
            boolean negative = character == '-';
            if (negative) {
                character = input.read();
            }
            // long позволяет прочитать модуль Integer.MIN_VALUE без переполнения.
            long value = 0;
            while (character > ' ') {
                value = value * 10 + character - '0';
                character = input.read();
            }
            return (int) (negative ? -value : value);
        }

        int[] readArray(int length) throws IOException {
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                values[i] = nextInt();
            }
            return values;
        }

        int[][] readPairs(int count) throws IOException {
            int[][] pairs = new int[count][2];
            for (int[] pair : pairs) {
                pair[0] = nextInt();
                pair[1] = nextInt();
            }
            return pairs;
        }
    }
}
