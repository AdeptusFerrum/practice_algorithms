package kt10;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JobSchedulingTest {
    @Test
    void choosesMostProfitableFeasibleOrdersWithoutChangingInput() {
        int[][] jobs = {{1, 20}, {2, 50}, {1, 40}};
        assertEquals(90, JobScheduling.maxProfit(jobs));
        assertArrayEquals(new int[][]{{1, 20}, {2, 50}, {1, 40}}, jobs);
    }

    @Test
    void oneAvailableSlotKeepsOnlyLargestProfit() {
        assertEquals(50, JobScheduling.maxProfit(new int[][]{{1, 20}, {1, 50}, {1, 40}}));
    }

    @Test
    void respectsEarlyDeadlinesEvenWhenLaterSlotsExist() {
        assertEquals(160, JobScheduling.maxProfit(new int[][]{{1, 90}, {1, 80}, {2, 70}, {2, 60}}));
    }

    @Test
    void largeDeadlinesAllowAllJobsWithoutAllocatingEverySlot() {
        assertEquals(60, JobScheduling.maxProfit(new int[][]{{1_000_000_000, 10}, {1_000_000_000, 20}, {3, 30}}));
    }

    @Test
    void handlesMaximumSizeAndLongProfit() {
        int[][] jobs = new int[500_000][2];
        for (int[] job : jobs) {
            job[0] = 1_000_000_000;
            job[1] = 1_000_000_000;
        }
        assertEquals(500_000_000_000_000L, JobScheduling.maxProfit(jobs));
    }

    @Test
    void matchesEnumerationOfAllSubsets() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int[][] jobs = new int[1 + random.nextInt(12)][2];
            for (int[] job : jobs) {
                job[0] = 1 + random.nextInt(jobs.length);
                job[1] = 1 + random.nextInt(30);
            }
            long best = 0;
            for (int mask = 0; mask < 1 << jobs.length; mask++) {
                int[] deadlines = new int[Integer.bitCount(mask)];
                int count = 0;
                long profit = 0;
                for (int i = 0; i < jobs.length; i++) {
                    if ((mask & (1 << i)) != 0) {
                        deadlines[count++] = jobs[i][0];
                        profit += jobs[i][1];
                    }
                }
                Arrays.sort(deadlines);
                boolean valid = true;
                for (int i = 0; i < deadlines.length; i++) {
                    if (deadlines[i] < i + 1) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    best = Math.max(best, profit);
                }
            }
            assertEquals(best, JobScheduling.maxProfit(jobs));
        }
    }
}
