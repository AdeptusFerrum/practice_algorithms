package kt8;
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
    void readsHeightsAndPrintsLongCost() throws IOException {
        assertEquals("4000000000\n", run("6\n-1000000000 -1000000000 1000000000 1000000000 -1000000000 -1000000000", "1"));
    }

    @Test
    void readsItemsAndCapacity() throws IOException {
        assertEquals("90\n", run("3 8\n3 30\n4 50\n5 60\n", "2"));
        assertEquals("3000000000\n", run("3 3\n1 1000000000\n1 1000000000\n1 1000000000", "2"));
    }

    @Test
    void readsStringsAndPrintsCommonLength() throws IOException {
        assertEquals("3\n", run("abcde\nace\n", "3"));
        assertEquals("0\n", run("a\nz", "3"));
    }

    @Test
    void printsExactProfitAndMinimumCount() throws IOException {
        assertEquals("12 1\n", run("3 6\n2 4\n3 6\n6 12\n", "4"));
        assertEquals("4000000000 4\n", run("1 4\n1 1000000000\n", "4"));
    }

    @Test
    void printsMinusOneForUnreachableMass() throws IOException {
        assertEquals("-1\n", run("2 7\n4 7\n6 13\n", "4"));
    }

    @Test
    void printsIncreasingLengthAndCount() throws IOException {
        assertEquals("4 2\n", run("5\n1 3 5 4 7\n", "5"));
        assertEquals("1 4\n", run("4\n-5 -5 -5 -5", "5"));
    }

    @Test
    void printsLexicographicallySmallestSupersequence() throws IOException {
        assertEquals("aba\n", run("ab\nba\n", "6"));
    }

    @Test
    void acceptsBlankLinesAndTabs() throws IOException {
        assertEquals("90\n", run("\n3\t8\n3 30\n\n4\t50\n5 60", "2"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Platform jumps: 30
                2. Expedition knapsack: 90
                3. Common subsequence: 3
                4. Exact cargo: 12 1
                5. Increasing subsequences: 4 2
                6. Shortest common supersequence: aba
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
