package kt10;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinMaxStackTest {
    @Test
    void restoresPreviousExtremesAfterPop() {
        MinMaxStack stack = new MinMaxStack(3);
        stack.push(3);
        stack.push(-2);
        stack.push(7);
        assertEquals(-2, stack.min());
        assertEquals(7, stack.max());
        stack.pop();
        assertEquals(-2, stack.min());
        assertEquals(3, stack.max());
        stack.pop();
        assertEquals(3, stack.min());
        assertEquals(3, stack.max());
    }

    @Test
    void keepsDuplicateExtremesUntilTheirLastOccurrenceIsRemoved() {
        MinMaxStack stack = new MinMaxStack(4);
        stack.push(5);
        stack.push(1);
        stack.push(1);
        stack.pop();
        assertEquals(1, stack.min());
        stack.push(5);
        stack.pop();
        assertEquals(5, stack.max());
        stack.pop();
        assertEquals(5, stack.min());
    }

    @Test
    void reusesStorageAfterBecomingEmpty() {
        MinMaxStack stack = new MinMaxStack(1);
        stack.push(-5);
        stack.pop();
        stack.push(9);
        assertEquals(9, stack.min());
        assertEquals(9, stack.max());
    }

    @Test
    void handlesBoundaryValues() {
        MinMaxStack stack = new MinMaxStack(2);
        stack.push(-1_000_000_000);
        stack.push(1_000_000_000);
        assertEquals(-1_000_000_000, stack.min());
        assertEquals(1_000_000_000, stack.max());
    }

    @Test
    void handlesMaximumCapacity() {
        MinMaxStack stack = new MinMaxStack(1_000_000);
        for (int i = 0; i < 1_000_000; i++) {
            stack.push(i);
        }
        assertEquals(0, stack.min());
        assertEquals(999_999, stack.max());
        for (int i = 0; i < 999_999; i++) {
            stack.pop();
        }
        assertEquals(0, stack.min());
        assertEquals(0, stack.max());
    }

    @Test
    void matchesListModelAfterMixedOperations() {
        Random random = new Random(42);
        MinMaxStack stack = new MinMaxStack(5_000);
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 5_000; i++) {
            if (values.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt(101) - 50;
                values.add(value);
                stack.push(value);
            } else {
                values.remove(values.size() - 1);
                stack.pop();
            }
            if (!values.isEmpty()) {
                assertEquals(Collections.min(values).intValue(), stack.min());
                assertEquals(Collections.max(values).intValue(), stack.max());
            }
        }
    }
}
