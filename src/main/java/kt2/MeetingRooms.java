package kt2;
import java.util.Arrays;

public class MeetingRooms {
    public static int minimum(int[][] meetings) {
        int[] starts = new int[meetings.length];
        int[] ends = new int[meetings.length];
        for (int i = 0; i < meetings.length; i++) {
            starts[i] = meetings[i][0];
            ends[i] = meetings[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        int start = 0;
        int end = 0;
        int active = 0;
        int maximum = 0;
        while (start < meetings.length) {
            if (starts[start] < ends[end]) {
                active++;
                maximum = Math.max(maximum, active);
                start++;
            } else {
                // При равном времени сначала освобождаем комнату: конец встречи не входит в интервал.
                active--;
                end++;
            }
        }
        return maximum;
    }
}
