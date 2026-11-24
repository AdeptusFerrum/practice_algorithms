package kt4;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void printsLevelSumsSeparatedBySpaces() throws IOException {
        assertEquals("1000000000 2000000000 3000000000\n", run("""
                6
                1000000000 1 2
                1000000000 3 4
                1000000000 5 -1
                1000000000 -1 -1
                1000000000 -1 -1
                1000000000 -1 -1
                """, "1"));
    }

    @Test
    void printsFrequentNumbersInRequiredOrder() throws IOException {
        assertEquals("7 -2 1\n", run("9 3\n4 7 1 4 -2 7 1 -2 7\n", "2"));
    }

    @Test
    void printsRunningMediansWithOneDecimal() throws IOException {
        assertEquals("-1.0 -0.5 -1.0 -1.5\n", run("4\n-1 0 -2 -3\n", "3"));
        assertEquals("1000000000.0\n", run("1\n1000000000\n", "3"));
    }

    @Test
    void printsLongPathSum() throws IOException {
        assertEquals("3000000000\n", run("""
                3
                1000000000 1 2
                1000000000 -1 -1
                1000000000 -1 -1
                """, "4"));
        assertEquals("-7\n", run("1\n-7 -1 -1\n", "4"));
    }

    @Test
    void readsTwoArraysAndCountsRepeatedPairs() throws IOException {
        assertEquals("3\n", run("3 2 5\n1 1 2\n1 2\n", "5"));
    }

    @Test
    void printsSlidingMediansWithOneDecimal() throws IOException {
        assertEquals("1.0 -1.0 -1.0 3.0 5.0 6.0\n", run("8 3\n1 3 -1 -3 5 3 6 7\n", "6"));
        assertEquals("-0.5\n", run("2 2\n\n-1\t0", "6"));
    }

    @Test
    void decimalSeparatorDoesNotDependOnLocale() throws IOException {
        Locale original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.GERMANY);
            assertEquals("1.0 1.5\n", run("2\n1 2\n", "3"));
            assertEquals("1.5\n", run("2 2\n1 2\n", "6"));
        } finally {
            Locale.setDefault(original);
        }
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Tree level sums: [-10, 29, 22]
                2. Top K frequent: [1, 2]
                3. Running median: [1.0, 1.5, 2.0, 2.5]
                4. Max tree path sum: 42
                5. Kth pair sum: 9
                6. Sliding window median: [1.0, -1.0, -1.0, 3.0, 5.0, 6.0]
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
