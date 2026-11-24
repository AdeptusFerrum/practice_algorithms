package kt3;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class NextHigherPriceTest {
    @Test
    void findsFirstHigherPrice() {
        assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0},
                NextHigherPrice.waitingDays(new int[]{73, 74, 75, 71, 69, 72, 76, 73}));
    }

    @Test
    void equalPriceDoesNotCount() {
        assertArrayEquals(new int[]{2, 1, 0, 0},
                NextHigherPrice.waitingDays(new int[]{5, 5, 6, 6}));
    }

    @Test
    void decreasingPricesHaveNoAnswer() {
        assertArrayEquals(new int[]{0, 0, 0}, NextHigherPrice.waitingDays(new int[]{3, 2, 1}));
    }

    @Test
    void singleDay() {
        assertArrayEquals(new int[]{0}, NextHigherPrice.waitingDays(new int[]{7}));
    }

    @Test
    void handlesValueLimitsWithoutChangingInput() {
        int[] prices = {1_000_000_000, 1, 1_000_000_000};
        assertArrayEquals(new int[]{0, 1, 0}, NextHigherPrice.waitingDays(prices));
        assertArrayEquals(new int[]{1_000_000_000, 1, 1_000_000_000}, prices);
    }

    @Test
    void handlesMaximumLength() {
        int[] prices = IntStream.rangeClosed(1, 200_000).toArray();
        int[] expected = new int[prices.length];
        Arrays.fill(expected, 1);
        expected[expected.length - 1] = 0;
        assertArrayEquals(expected, NextHigherPrice.waitingDays(prices));
    }
}
