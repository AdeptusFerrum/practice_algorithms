package kt6;
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
    void readsAndPrintsReversedList() throws IOException {
        assertEquals("1000000000 0 -1000000000\n", run("3\n-1000000000 0 1000000000\n", "1"));
        assertEquals("-7\n", run("1\n\n-7", "1"));
    }

    @Test
    void processesStationConnections() throws IOException {
        assertEquals("NO\nYES\nYES\nNO\n", run("""
                4 8
                2 1 2
                1 1 2
                1 2 3
                2 3 1
                2 4 4
                1 1 2
                2 1 4
                1 4 3
                """, "2"));
    }

    @Test
    void printsSmallestStageOrderOrMinusOne() throws IOException {
        assertEquals("2 1 3\n", run("3 2\n2 1\n2 1\n", "3"));
        assertEquals("-1\n", run("3 2\n1 2\n2 1\n", "3"));
        assertEquals("1 2 3\n", run("3 0\n", "3"));
    }

    @Test
    void readsBlockSizeAndPreservesIncompleteTail() throws IOException {
        assertEquals("3 2 1 4 5\n", run("5 3\n1 2 3 4 5\n", "4"));
        assertEquals("1 2 3\n", run("3 4\n1\t2\t3\n", "4"));
        assertEquals("7\n", run("1 1\n7\n", "4"));
    }

    @Test
    void printsNoForEveryStatementAfterContradiction() throws IOException {
        assertEquals("YES\nYES\nYES\nNO\nNO\nNO\n", run("""
                3 6
                1 2 1
                2 3 1
                1 3 0
                1 3 1
                2 2 0
                1 2 1
                """, "5"));
    }

    @Test
    void printsAlphabetOrImpossible() throws IOException {
        assertEquals("bac\n", run("3 3\nba\nbc\nac\n", "6"));
        assertEquals("IMPOSSIBLE\n", run("2 3\nabc\nab\n", "6"));
        assertEquals("IMPOSSIBLE\n", run("3 2\na\nb\na\n", "6"));
    }

    @Test
    void printsNothingWithoutConnectionQueries() throws IOException {
        assertEquals("", run("2 2\n1 1 2\n1 2 1\n", "2"));
    }

    @Test
    void handlesMaximumQueryCount() throws IOException {
        assertEquals("YES\n".repeat(200_000),
                run("1 200000\n" + "2 1 1\n".repeat(200_000), "2"));
        assertEquals("NO\n".repeat(200_000),
                run("1 200000\n1 1 1\n" + "1 1 0\n".repeat(199_999), "5"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Reversed list: 5 4 3 2 1
                2. Station connections: YES
                3. Stage order: [1, 2, 3, 4]
                4. Reversed groups: 2 1 4 3 5
                5. Group constraints: [YES, YES, NO]
                6. Alien alphabet: bac
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
