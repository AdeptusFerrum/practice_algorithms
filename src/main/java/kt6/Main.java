package kt6;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
            case 1 -> printList(ReverseLinkedList.reverse(input.readList(input.nextInt())));
            case 2 -> {
                int n = input.nextInt();
                int q = input.nextInt();
                StationConnections stations = new StationConnections(n);
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < q; i++) {
                    int type = input.nextInt();
                    int u = input.nextInt();
                    int v = input.nextInt();
                    if (type == 1) {
                        stations.connect(u, v);
                    } else if (type == 2) {
                        output.append(stations.connected(u, v) ? "YES\n" : "NO\n");
                    } else {
                        throw new IllegalArgumentException("Unknown operation: " + type);
                    }
                }
                System.out.print(output);
            }
            case 3 -> {
                int n = input.nextInt();
                int m = input.nextInt();
                int[][] requirements = new int[m][2];
                for (int[] requirement : requirements) {
                    requirement[0] = input.nextInt();
                    requirement[1] = input.nextInt();
                }
                int[] order = StageOrder.find(n, requirements);
                if (order.length == 0) {
                    System.out.println(-1);
                } else {
                    StringBuilder output = new StringBuilder();
                    for (int stage : order) {
                        if (!output.isEmpty()) {
                            output.append(' ');
                        }
                        output.append(stage);
                    }
                    System.out.println(output);
                }
            }
            case 4 -> {
                int n = input.nextInt();
                int k = input.nextInt();
                printList(ReverseKGroup.reverse(input.readList(n), k));
            }
            case 5 -> {
                int n = input.nextInt();
                int q = input.nextInt();
                GroupConstraints groups = new GroupConstraints(n);
                StringBuilder output = new StringBuilder();
                for (int i = 0; i < q; i++) {
                    boolean possible = groups.add(input.nextInt(), input.nextInt(), input.nextInt());
                    output.append(possible ? "YES\n" : "NO\n");
                }
                System.out.print(output);
            }
            case 6 -> {
                int n = input.nextInt();
                int k = input.nextInt();
                String[] words = new String[n];
                for (int i = 0; i < n; i++) {
                    words[i] = input.next();
                }
                System.out.println(AlienAlphabet.find(words, k));
            }
            default -> throw new IllegalArgumentException("Task number must be between 1 and 6");
        }
    }

    private static void printList(ListNode head) throws IOException {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        for (ListNode node = head; node != null; node = node.next) {
            if (node != head) {
                output.write(' ');
            }
            output.write(Integer.toString(node.value));
        }
        output.newLine();
        output.flush();
    }

    private static void printExamples() throws IOException {
        System.out.print("1. Reversed list: ");
        printList(ReverseLinkedList.reverse(exampleList()));
        StationConnections stations = new StationConnections(3);
        stations.connect(1, 2);
        stations.connect(2, 3);
        System.out.println("2. Station connections: " + (stations.connected(1, 3) ? "YES" : "NO"));
        System.out.println("3. Stage order: "
                + Arrays.toString(StageOrder.find(4, new int[][]{{1, 3}, {2, 3}, {3, 4}})));
        System.out.print("4. Reversed groups: ");
        printList(ReverseKGroup.reverse(exampleList(), 2));
        GroupConstraints groups = new GroupConstraints(3);
        System.out.println("5. Group constraints: ["
                + (groups.add(1, 2, 1) ? "YES" : "NO") + ", "
                + (groups.add(2, 3, 1) ? "YES" : "NO") + ", "
                + (groups.add(1, 3, 1) ? "YES" : "NO") + "]");
        System.out.println("6. Alien alphabet: " + AlienAlphabet.find(new String[]{"ba", "bc", "ac"}, 3));
    }

    private static ListNode exampleList() {
        ListNode head = null;
        for (int value = 5; value >= 1; value--) {
            ListNode node = new ListNode(value);
            node.next = head;
            head = node;
        }
        return head;
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

        ListNode readList(int length) throws IOException {
            ListNode head = null;
            ListNode tail = null;
            for (int i = 0; i < length; i++) {
                ListNode node = new ListNode(nextInt());
                if (head == null) {
                    head = node;
                } else {
                    tail.next = node;
                }
                tail = node;
            }
            return head;
        }
    }
}
