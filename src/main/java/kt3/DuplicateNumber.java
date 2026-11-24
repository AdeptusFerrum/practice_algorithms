package kt3;
public class DuplicateNumber {
    public static int find(int[] nums) {
        int slow = 0;
        int fast = 0;

        // Значение задаёт следующий индекс. Повторяющееся число образует вход в цикл.
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        int entry = 0;
        while (entry != slow) {
            entry = nums[entry];
            slow = nums[slow];
        }
        return entry;
    }
}
