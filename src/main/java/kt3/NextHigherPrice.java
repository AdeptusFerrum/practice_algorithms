package kt3;
public class NextHigherPrice {
    public static int[] waitingDays(int[] prices) {
        int[] result = new int[prices.length];
        int[] stack = new int[prices.length];
        int size = 0;

        for (int day = 0; day < prices.length; day++) {
            // В стеке остаются индексы дней, для которых ещё не найдена строго большая цена.
            while (size > 0 && prices[day] > prices[stack[size - 1]]) {
                int previous = stack[--size];
                result[previous] = day - previous;
            }
            stack[size++] = day;
        }
        return result;
    }
}
