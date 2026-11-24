package kt3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CycleDetectionTest {
    @Test
    void chainEndsWithoutCycle() {
        assertFalse(CycleDetection.hasCycle(new int[]{2, 3, -1}, 1));
        assertFalse(CycleDetection.hasCycle(new int[]{-1}, 1));
    }

    @Test
    void detectsSelfLoop() {
        assertTrue(CycleDetection.hasCycle(new int[]{1}, 1));
    }

    @Test
    void detectsCycleAfterChainWithoutChangingInput() {
        int[] next = {2, 3, 4, 2};
        assertTrue(CycleDetection.hasCycle(next, 1));
        assertArrayEquals(new int[]{2, 3, 4, 2}, next);
    }

    @Test
    void ignoresUnreachableCycle() {
        int[] next = {2, -1, 4, 3};
        assertFalse(CycleDetection.hasCycle(next, 1));
        assertTrue(CycleDetection.hasCycle(next, 3));
    }

    @Test
    void acceptsLastNodeAsStart() {
        assertFalse(CycleDetection.hasCycle(new int[]{2, -1, -1}, 3));
        assertTrue(CycleDetection.hasCycle(new int[]{-1, -1, 3}, 3));
    }

    @Test
    void handlesMaximumLength() {
        int[] next = new int[1_000_000];
        for (int i = 0; i < next.length - 1; i++) {
            next[i] = i + 2;
        }
        next[next.length - 1] = -1;
        assertFalse(CycleDetection.hasCycle(next, 1));
        next[next.length - 1] = 500_000;
        assertTrue(CycleDetection.hasCycle(next, 1));
    }
}
