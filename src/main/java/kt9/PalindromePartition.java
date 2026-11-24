package kt9;
public class PalindromePartition {
    public static int minParts(String value) {
        int n = value.length();
        int[] parts = new int[n + 1];
        boolean[] palindrome = new boolean[n];
        for (int end = 0; end < n; end++) {
            parts[end + 1] = end + 1;
            for (int start = 0; start <= end; start++) {
                // При обходе слева направо palindrome[start + 1] ещё относится к концу end - 1.
                palindrome[start] = value.charAt(start) == value.charAt(end)
                        && (end - start < 2 || palindrome[start + 1]);
                if (palindrome[start]) {
                    parts[end + 1] = Math.min(parts[end + 1], parts[start] + 1);
                }
            }
        }
        return parts[n];
    }
}
