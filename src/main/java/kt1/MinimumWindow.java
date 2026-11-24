package kt1;
public class MinimumWindow {
    public static String find(String source, String target) {
        if (target.isEmpty() || target.length() > source.length()) {
            return "";
        }

        // По условию алфавит латинские буквы + цифры, поэтому достаточно массива частот ASCII.
        int[] needed = new int[128];
        for (int i = 0; i < target.length(); i++) {
            needed[target.charAt(i)]++;
        }

        int missing = target.length();
        int left = 0;
        int bestStart = 0;
        int bestLength = source.length() + 1;

        for (int right = 0; right < source.length(); right++) {
            char added = source.charAt(right);
            if (needed[added] > 0) {
                missing--;
            }
            needed[added]--; // Отрицательное значение означает лишние вхождения символа.

            while (missing == 0) {
                int length = right - left + 1;
                if (length < bestLength) {
                    bestStart = left;
                    bestLength = length;
                }

                char removed = source.charAt(left++);
                needed[removed]++;
                if (needed[removed] > 0) {
                    missing++;
                }
            }
        }
        return bestLength > source.length()
                ? ""
                : source.substring(bestStart, bestStart + bestLength);
    }
}
