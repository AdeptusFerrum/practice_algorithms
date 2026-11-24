package kt5;
public class TwoUniqueNumbers {
    public static int[] find(int[] nums) {
        int xor = 0;
        for (int number : nums) {
            xor ^= number;
        }

        // Этот бит различает два одиночных числа. Парные числа попадут в одну группу.
        int differingBit = xor & -xor;
        int first = 0;
        int second = 0;
        for (int number : nums) {
            if ((number & differingBit) == 0) {
                first ^= number;
            } else {
                second ^= number;
            }
        }
        return new int[]{Math.min(first, second), Math.max(first, second)};
    }
}
