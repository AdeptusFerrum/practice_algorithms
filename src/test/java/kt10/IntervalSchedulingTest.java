package kt10;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntervalSchedulingTest {
    @Test
    void acceptsTouchingIntervalsAndPreservesInput() {
        int[][] requests = {{3, 5}, {1, 3}, {2, 4}, {0, 7}};
        assertEquals(2, IntervalScheduling.maxCount(requests));
        assertArrayEquals(new int[][]{{3, 5}, {1, 3}, {2, 4}, {0, 7}}, requests);
    }

    @Test
    void skipsLongIntervalInFavorOfSeveralShortOnes() {
        assertEquals(3, IntervalScheduling.maxCount(new int[][]{{0, 10}, {1, 2}, {2, 3}, {3, 4}}));
    }

    @Test
    void handlesDuplicateIntervalsAndEqualEnds() {
        assertEquals(2, IntervalScheduling.maxCount(new int[][]{{1, 3}, {1, 3}, {2, 3}, {3, 5}}));
    }

    @Test
    void handlesSingleRequestAndBoundaryTimes() {
        assertEquals(1, IntervalScheduling.maxCount(new int[][]{{0, 1_000_000_000}}));
    }

    @Test
    void handlesMaximumNumberOfRequests() {
        int[][] requests = new int[200_000][2];
        for (int i = 0; i < requests.length; i++) {
            requests[i] = new int[]{requests.length - i - 1, requests.length - i};
        }
        assertEquals(requests.length, IntervalScheduling.maxCount(requests));
    }

    @Test
    void matchesEnumerationOfAllSubsets() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int[][] requests = new int[1 + random.nextInt(12)][2];
            for (int[] request : requests) {
                request[0] = random.nextInt(10);
                request[1] = request[0] + 1 + random.nextInt(5);
            }
            int best = 0;
            for (int mask = 0; mask < 1 << requests.length; mask++) {
                boolean valid = true;
                for (int i = 0; i < requests.length && valid; i++) {
                    if ((mask & (1 << i)) == 0) {
                        continue;
                    }
                    for (int j = i + 1; j < requests.length; j++) {
                        if ((mask & (1 << j)) != 0
                                && requests[i][0] < requests[j][1] && requests[j][0] < requests[i][1]) {
                            valid = false;
                            break;
                        }
                    }
                }
                if (valid) {
                    best = Math.max(best, Integer.bitCount(mask));
                }
            }
            assertEquals(best, IntervalScheduling.maxCount(requests));
        }
    }
}
