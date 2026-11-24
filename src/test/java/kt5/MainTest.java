package kt5;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void printsEachSequenceOnItsOwnLine() throws IOException {
        assertEquals("((()))\n(()())\n(())()\n()(())\n()()()\n", run("3\n", "1"));
        assertEquals("()\n", run("1\n", "1"));
    }

    @Test
    void processesPrefixQueriesAndRemovals() throws IOException {
        assertEquals("0\n3\n2\n1\n", run("""
                9
                COUNT app
                ADD app
                ADD apple
                ADD apply
                COUNT app
                REMOVE app
                COUNT app
                REMOVE apple
                COUNT app
                """, "2"));
    }

    @Test
    void printsUniqueNumbersInAscendingOrder() throws IOException {
        assertEquals("3 5\n", run("6\n1 2 1 3 2 5\n", "3"));
        assertEquals("0 1000000000\n", run("2\n\n1000000000\t0", "3"));
    }

    @Test
    void readsOneBasedBlockedCells() throws IOException {
        assertEquals("1\n", run("4 1\n1 2\n", "4"));
        assertEquals("2\n", run("4 0\n", "4"));
        assertEquals("0\n", run("1 1\n1 1\n", "4"));
    }

    @Test
    void processesKthQueriesAndPrintsNone() throws IOException {
        assertEquals("NONE\napp\napple\napple\nNONE\napply\n", run("""
                12
                KTH app 1
                ADD apply
                ADD app
                ADD apple
                KTH app 1
                KTH app 2
                REMOVE app
                KTH app 1
                KTH app 3
                REMOVE apple
                ADD app
                KTH app 2
                """, "5"));
    }

    @Test
    void printsMaximumSubarrayXor() throws IOException {
        assertEquals("7\n", run("4\n3 1 6 3\n", "6"));
        assertEquals("1073741823\n", run("2\n536870912 536870911\n", "6"));
    }

    @Test
    void printsNothingWhenThereAreNoQueries() throws IOException {
        assertEquals("", run("2\nADD a\nREMOVE a\n", "2"));
        assertEquals("", run("2\nADD a\nREMOVE a\n", "5"));
    }

    @Test
    void handlesManyQueries() throws IOException {
        assertEquals("0\n".repeat(100_000), run("100000\n" + "COUNT a\n".repeat(100_000), "2"));
        assertEquals("NONE\n".repeat(100_000), run("100000\n" + "KTH a 1\n".repeat(100_000), "5"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Balanced parentheses: [((())), (()()), (())(), ()(()), ()()()]
                2. Prefix count: 1
                3. Two unique numbers: [3, 5]
                4. Blocked queens: 1
                5. Kth word: apple
                6. Max subarray XOR: 7
                """, run(""));
    }

    private String run(String input, String... args) throws IOException {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(capture);
            Main.main(args);
            return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}
