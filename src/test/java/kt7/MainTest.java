package kt7;
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
    void printsDistancesAndInfSeparatedBySpaces() throws IOException {
        assertEquals("0 1000000000 2000000000 3000000000 INF\n", run("""
                5 3 1
                1 2 1000000000
                2 3 1000000000
                3 4 1000000000
                """, "1"));
    }

    @Test
    void readsNegativeWeightsAndPrintsLongCosts() throws IOException {
        assertEquals("0 -1000000000 -2000000000 -3000000000 INF\n", run("""
                5 3 1
                3 4 -1000000000
                2 3 -1000000000
                1 2 -1000000000
                """, "2"));
    }

    @Test
    void printsNearestPointAndDistanceOnSeparateLines() throws IOException {
        assertEquals("1 0\n1 2\n3 0\n-1 -1\n", run("""
                4 2 2
                3 1
                1 2 2
                3 2 2
                """, "3"));
    }

    @Test
    void printsDiscountedCostOrMinusOne() throws IOException {
        assertEquals("7\n", run("3 2 1 3\n1 2 5\n2 3 5\n", "4"));
        assertEquals("-1\n", run("2 0 1 2\n", "4"));
        assertEquals("0\n", run("1 0 1 1\n", "4"));
    }

    @Test
    void printsFiniteUnreachableAndUnboundedStates() throws IOException {
        assertEquals("0\n-\n-\n5\n*\n", run("""
                5 4 1
                1 2 1
                2 3 -2
                3 2 1
                1 4 5
                """, "5"));
        assertEquals("0\n-3\n", run("2 1 1\n1 2 -3\n", "5"));
    }

    @Test
    void printsBoundedCostAndRouteCount() throws IOException {
        assertEquals("2 3\n", run("""
                3 4 1 3 2
                1 3 2
                1 3 2
                1 2 1
                2 3 1
                """, "6"));
    }

    @Test
    void printsImpossibleOrEmptyRouteForZeroLimit() throws IOException {
        assertEquals("IMPOSSIBLE\n", run("2 1 1 2 0\n1 2 0\n", "6"));
        assertEquals("0 1\n", run("1 0 1 1 0\n", "6"));
    }

    @Test
    void acceptsBlankLinesAndTabs() throws IOException {
        assertEquals("INF 0 INF\n", run("3\t0\n\n2", "1"));
    }

    @Test
    void printsExamplesWithoutArguments() throws IOException {
        assertEquals("""
                1. Dijkstra: 0 3 1 4 INF
                2. Bellman-Ford: 0 4 2 INF
                3. Nearest help point (vertex 2): 1 2
                4. Discounted path: 7
                5. Negative cycles: 0 - - 5 *
                6. Bounded paths: 2 3
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
