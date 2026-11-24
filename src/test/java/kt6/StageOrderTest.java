package kt6;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class StageOrderTest {
    @Test
    void respectsRequirements() {
        assertArrayEquals(new int[]{1, 2, 3, 4},
                StageOrder.find(4, new int[][]{{1, 3}, {2, 3}, {3, 4}}));
    }

    @Test
    void noRequirementsMeansAscendingOrder() {
        assertArrayEquals(new int[]{1, 2, 3}, StageOrder.find(3, new int[0][2]));
        assertArrayEquals(new int[]{1}, StageOrder.find(1, new int[0][2]));
    }

    @Test
    void newlyAvailableSmallerStageGoesFirst() {
        assertArrayEquals(new int[]{2, 1, 3}, StageOrder.find(3, new int[][]{{2, 1}}));
    }

    @Test
    void repeatedRequirementsDoNotBlockStages() {
        assertArrayEquals(new int[]{1, 2, 3},
                StageOrder.find(3, new int[][]{{1, 2}, {1, 2}, {2, 3}, {2, 3}}));
    }

    @Test
    void cycleInSeparateComponentMakesWholeOrderImpossible() {
        assertArrayEquals(new int[0], StageOrder.find(4, new int[][]{{2, 3}, {3, 2}}));
    }

    @Test
    void selfDependencyIsImpossible() {
        assertArrayEquals(new int[0], StageOrder.find(1, new int[][]{{1, 1}}));
    }

    @Test
    void doesNotChangeRequirements() {
        int[][] requirements = {{3, 1}, {2, 1}};
        assertArrayEquals(new int[]{2, 3, 1}, StageOrder.find(3, requirements));
        assertArrayEquals(new int[][]{{3, 1}, {2, 1}}, requirements);
    }

    @Test
    void handlesMaximumStagesAndRequirementsWithoutRecursion() {
        int n = 200_000;
        int[][] requirements = new int[400_000][2];
        for (int i = 1; i < n; i++) {
            requirements[2 * (i - 1)] = new int[]{i, i + 1};
            requirements[2 * (i - 1) + 1] = new int[]{i, i + 1};
        }
        requirements[399_998] = new int[]{1, 2};
        requirements[399_999] = new int[]{1, 2};
        assertArrayEquals(IntStream.rangeClosed(1, n).toArray(), StageOrder.find(n, requirements));
    }
}
