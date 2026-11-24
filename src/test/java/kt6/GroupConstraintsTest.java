package kt6;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupConstraintsTest {
    @Test
    void combinesSameAndDifferentGroups() {
        GroupConstraints groups = new GroupConstraints(3);
        assertTrue(groups.add(1, 2, 0));
        assertTrue(groups.add(2, 3, 1));
        assertTrue(groups.add(1, 3, 1));
        assertFalse(groups.add(1, 3, 0));
    }

    @Test
    void oddCycleOfDifferentGroupsIsImpossible() {
        GroupConstraints groups = new GroupConstraints(3);
        assertTrue(groups.add(1, 2, 1));
        assertTrue(groups.add(2, 3, 1));
        assertFalse(groups.add(3, 1, 1));
    }

    @Test
    void evenCycleAndRepeatedStatementsAreCompatible() {
        GroupConstraints groups = new GroupConstraints(4);
        assertTrue(groups.add(1, 2, 1));
        assertTrue(groups.add(2, 3, 1));
        assertTrue(groups.add(3, 4, 1));
        assertTrue(groups.add(4, 1, 1));
        assertTrue(groups.add(2, 1, 1));
        assertTrue(groups.add(1, 3, 0));
    }

    @Test
    void selfContradictionKeepsAllFollowingAnswersNegative() {
        GroupConstraints groups = new GroupConstraints(3);
        assertTrue(groups.add(1, 1, 0));
        assertFalse(groups.add(1, 1, 1));
        assertFalse(groups.add(2, 3, 0));
        assertFalse(groups.add(2, 2, 0));
    }

    @Test
    void preservesRelationsWhenSmallerRootIsAttachedToLargerRoot() {
        GroupConstraints groups = new GroupConstraints(5);
        assertTrue(groups.add(1, 2, 1));
        assertTrue(groups.add(3, 4, 0));
        assertTrue(groups.add(3, 5, 1));
        assertTrue(groups.add(1, 3, 1));
        assertTrue(groups.add(2, 4, 0));
        assertTrue(groups.add(2, 5, 1));
        assertTrue(groups.add(1, 5, 0));
        assertFalse(groups.add(2, 3, 1));
    }

    @Test
    void matchesExhaustiveGroupAssignments() {
        Random random = new Random(42);
        int n = 6;
        for (int test = 0; test < 100; test++) {
            GroupConstraints groups = new GroupConstraints(n);
            List<int[]> statements = new ArrayList<>();
            for (int step = 0; step < 20; step++) {
                int u = 1 + random.nextInt(n);
                int v = 1 + random.nextInt(n);
                int relation = random.nextInt(2);
                statements.add(new int[]{u, v, relation});
                assertEquals(hasAssignment(n, statements), groups.add(u, v, relation));
            }
        }
    }

    @Test
    void instancesAreIndependent() {
        GroupConstraints first = new GroupConstraints(1);
        GroupConstraints second = new GroupConstraints(1);
        assertFalse(first.add(1, 1, 1));
        assertTrue(second.add(1, 1, 0));
    }

    @Test
    void handlesMaximumPeopleAndStatementCount() {
        GroupConstraints groups = new GroupConstraints(200_000);
        for (int i = 1; i < 200_000; i++) {
            assertTrue(groups.add(i, i + 1, 1));
        }
        assertFalse(groups.add(1, 200_000, 0));
    }

    private boolean hasAssignment(int n, List<int[]> statements) {
        for (int assignment = 0; assignment < (1 << n); assignment++) {
            boolean valid = true;
            for (int[] statement : statements) {
                int first = (assignment >>> (statement[0] - 1)) & 1;
                int second = (assignment >>> (statement[1] - 1)) & 1;
                if ((first ^ second) != statement[2]) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return true;
            }
        }
        return false;
    }
}
