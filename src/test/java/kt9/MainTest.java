package kt9;
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
    void readsStringAndPrintsNumberOfParts() throws IOException {
        assertEquals("2\n", run("aab\n", "1"));
        assertEquals("1\n", run("abba", "1"));
    }

    @Test
    void readsMapRowsAndCountsRoutes() throws IOException {
        assertEquals("2\n", run("3 3\n...\n.#.\n...\n", "2"));
    }

    @Test
    void printsZeroForBlockedStartOrFinish() throws IOException {
        assertEquals("0\n", run("1 1\n#\n", "2"));
        assertEquals("0\n", run("1 2\n.#\n", "2"));
    }

    @Test
    void readsStationValuesAndOneBasedEdges() throws IOException {
        assertEquals("3000000000\n", run("""
                4
                -1 1000000000 1000000000 1000000000
                1 2
                3 1
                1 4
                """, "3"));
        assertEquals("0\n", run("1\n-5\n", "3"));
    }

    @Test
    void printsLongMergeCostAndHandlesOneFragment() throws IOException {
        assertEquals("5000000000\n", run("3\n1000000000 1000000000 1000000000", "4"));
        assertEquals("0\n", run("1\n7\n", "4"));
    }

    @Test
    void readsShieldLimitAndSignedCells() throws IOException {
        assertEquals("2\n", run("2 2 2\n-5 2\n1 -7\n", "5"));
        assertEquals("-3000000000\n", run("1 3 0\n-1000000000 -1000000000 -1000000000", "5"));
    }

    @Test
    void readsKAndRequiresExactConnectedSelection() throws IOException {
        assertEquals("-7\n", run("3 2\n-5 -2 -8\n1 2\n2 3\n", "6"));
        assertEquals("3000000000\n", run("3 3\n1000000000 1000000000 1000000000\n1 2\n3 2", "6"));
    }

    @Test
    void acceptsBlankLinesAndTabs() throws IOException {
        assertEquals("90\n", run("\n3\n10\t20\n\n30", "4"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Palindrome partition: 2
                2. Grid routes: 2
                3. Independent stations: 15
                4. Adjacent merges: 90
                5. Shielded grid: 5
                6. Connected stations: 12
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
