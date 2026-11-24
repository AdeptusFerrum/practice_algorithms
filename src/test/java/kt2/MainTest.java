package kt2;
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
    void readsSearchInput() throws IOException {
        assertEquals("1\n", run("4 3\n1 3 3 5\n", "1"));
        assertEquals("0\n", run("0 7\n", "1"));
    }

    @Test
    void printsOneSumPerQuery() throws IOException {
        assertEquals("4\n7\n3\n", run("4\n2 -1 3 5\n3\n0 2\n1 3\n2 2\n", "2"));
    }

    @Test
    void printsMergedIntervals() throws IOException {
        assertEquals("1 7\n9 10\n", run("4\n5 7\n1 3\n3 5\n9 10\n", "3"));
        assertEquals("", run("0\n", "3"));
    }

    @Test
    void readsWeightsAndDays() throws IOException {
        assertEquals("3000000000\n", run("3 1\n1000000000 1000000000 1000000000\n", "4"));
    }

    @Test
    void readsLongTargetAndFlexibleWhitespace() throws IOException {
        assertEquals("1\n", run("3\t3000000000\n\n1000000000 1000000000\n1000000000", "5"));
    }

    @Test
    void readsMeetingIntervals() throws IOException {
        assertEquals("1\n", run("3\n0 5\n5 10\n10 15\n", "6"));
        assertEquals("0\n", run("0\n", "6"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Search insert position: 1
                2. Range sums: [4, 7, 3]
                3. Merge intervals: [[1, 7]]
                4. Ship capacity: 15
                5. Subarray sum: 3
                6. Meeting rooms: 2
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
