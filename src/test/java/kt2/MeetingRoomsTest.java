package kt2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MeetingRoomsTest {
    @Test
    void countsSimultaneousMeetings() {
        assertEquals(2, MeetingRooms.minimum(new int[][]{{0, 30}, {5, 10}, {15, 20}}));
        assertEquals(3, MeetingRooms.minimum(new int[][]{{0, 10}, {1, 9}, {2, 8}}));
    }

    @Test
    void touchingMeetingsShareRoom() {
        assertEquals(1, MeetingRooms.minimum(new int[][]{{0, 5}, {5, 10}, {10, 15}}));
    }

    @Test
    void freesAllRoomsAtSameTime() {
        assertEquals(2, MeetingRooms.minimum(new int[][]{{0, 5}, {0, 5}, {5, 10}, {5, 10}}));
    }

    @Test
    void emptyArrayAndSingleMeeting() {
        assertEquals(0, MeetingRooms.minimum(new int[0][]));
        assertEquals(1, MeetingRooms.minimum(new int[][]{{0, 1_000_000_000}}));
    }

    @Test
    void handlesUnsortedMeetingsWithoutChangingInput() {
        int[][] meetings = {{7, 10}, {2, 4}, {3, 8}};
        assertEquals(2, MeetingRooms.minimum(meetings));
        assertArrayEquals(new int[][]{{7, 10}, {2, 4}, {3, 8}}, meetings);
    }

    @Test
    void handlesMaximumNumberOfSimultaneousMeetings() {
        int[][] meetings = new int[200_000][2];
        for (int[] meeting : meetings) {
            meeting[1] = 1_000_000_000;
        }
        assertEquals(200_000, MeetingRooms.minimum(meetings));
    }
}
