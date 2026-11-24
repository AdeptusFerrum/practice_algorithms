package kt1;
public class Main {
    public static void main(String[] args) {
        System.out.println("1. Longest consecutive: "
                + LongestConsecutive.length(new int[]{100, 4, 200, 1, 3, 2}));
        System.out.println("2. Three sum: "
                + ThreeSum.find(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println("3. Two types window: "
                + TwoTypesWindow.maxLength(new int[]{1, 2, 3, 2, 2}));
        System.out.println("4. Subarray sum: "
                + SubarraySum.count(new int[]{1, -1, 0}, 0));
        System.out.println("5. Max water container: "
                + MaxWaterContainer.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println("6. Minimum window: "
                + MinimumWindow.find("ADOBECODEBANC", "ABC"));
    }
}
