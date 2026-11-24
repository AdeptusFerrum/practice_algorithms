package kt10;
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
    void readsIntervalsAndPrintsMaximumCount() throws IOException {
        assertEquals("2\n", run("4\n3 5\n1 3\n2 4\n0 7", "1"));
    }

    @Test
    void printsOnlyMinAndMaxAnswers() throws IOException {
        assertEquals("-2\n7\n3\n3\n", run("""
                9
                PUSH 3
                PUSH -2
                MIN
                PUSH 7
                MAX
                POP
                MAX
                POP
                MIN
                """, "2"));
    }

    @Test
    void stackUpdatesWithoutQueriesProduceNoOutput() throws IOException {
        assertEquals("", run("2\nPUSH 5\nPOP", "2"));
    }

    @Test
    void readsFullSignedIntegerRangeAndOneBasedK() throws IOException {
        assertEquals("-2147483648\n", run("3 1\n2147483647 0 -2147483648", "3"));
        assertEquals("2147483647\n", run("3 3\n2147483647 0 -2147483648", "3"));
    }

    @Test
    void printsLongProfit() throws IOException {
        assertEquals("3000000000\n", run("3\n3 1000000000\n3 1000000000\n3 1000000000", "4"));
    }

    @Test
    void processesCacheQueriesAndUpdates() throws IOException {
        assertEquals("10\n-1\n30\n11\n", run("""
                2 9
                PUT 1 10
                PUT 2 20
                GET 1
                PUT 3 30
                GET 2
                GET 3
                PUT 1 11
                PUT 4 40
                GET 1
                """, "5"));
    }

    @Test
    void cacheUpdatesWithoutQueriesProduceNoOutput() throws IOException {
        assertEquals("", run("1 2\nPUT 0 0\nPUT 0 7", "5"));
    }

    @Test
    void printsCoordinatesAndOriginalIndicesInRequiredOrder() throws IOException {
        assertEquals("0 0 6\n-1 0 2\n-1 0 5\n0 -1 4\n", run("""
                6 4
                1 0
                -1 0
                0 1
                0 -1
                -1 0
                0 0
                """, "6"));
    }

    @Test
    void readsOneLongLineAcrossMultipleBufferFills() throws IOException {
        StringBuilder input = new StringBuilder("20000 10001\n");
        for (int i = 0; i < 20_000; i++) {
            input.append(i - 10_000).append(' ');
        }
        assertEquals("0\n", run(input.toString(), "3"));
    }

    @Test
    void acceptsBlankLinesTabsAndCrLf() throws IOException {
        assertEquals("3\n", run("\r\n5\t3\r\n7 1\t5\r\n\r\n3 3", "3"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Interval scheduling: 2
                2. Min/max stack: -2 7
                3. Kth element: 3
                4. Job scheduling: 90
                5. LFU cache: -1 30
                6. Nearest points: (-1, 0, 2) (0, 1, 3)
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
