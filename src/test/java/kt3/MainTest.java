package kt3;
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
    void printsWaitingDaysSeparatedBySpaces() throws IOException {
        assertEquals("1 1 4 2 1 1 0 0\n", run("8\n73 74 75 71 69 72 76 73\n", "1"));
        assertEquals("0\n", run("1\n1000000000\n", "1"));
    }

    @Test
    void readsMapWithWalls() throws IOException {
        assertEquals("4\n", run("2 3\nS#T\n...\n", "2"));
        assertEquals("-1\n", run("1 3\nS#T\n", "2"));
    }

    @Test
    void readsOneBasedNodesAndStart() throws IOException {
        assertEquals("YES\n", run("4 3\n2 -1 4 3\n", "3"));
        assertEquals("NO\n", run("4 1\n2 -1 4 3\n", "3"));
    }

    @Test
    void readsBinaryRowsWithoutSpaces() throws IOException {
        assertEquals("6\n", run("4 5\n10100\n10111\n11111\n10010\n", "4"));
    }

    @Test
    void readsKeysAndDoors() throws IOException {
        assertEquals("6\n", run("1 5\na.SAT\n", "5"));
        assertEquals("-1\n", run("1 4\nSAaT\n", "5"));
    }

    @Test
    void readsNPlusOneNumbers() throws IOException {
        assertEquals("2\n", run("4\n1 3 4 2 2\n", "6"));
        assertEquals("1\n", run("1\n\n1\t1", "6"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Next higher price: [1, 1, 4, 2, 1, 1, 0, 0]
                2. Grid shortest path: 4
                3. Cycle detection: YES
                4. Maximal rectangle: 6
                5. Key maze: 6
                6. Duplicate number: 2
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
