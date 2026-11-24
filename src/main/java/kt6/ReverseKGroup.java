package kt6;
public class ReverseKGroup {
    public static ListNode reverse(ListNode head, int k) {
        ListNode current = head;
        ListNode previousTail = null;
        while (current != null) {
            ListNode blockEnd = current;
            for (int i = 1; i < k && blockEnd != null; i++) {
                blockEnd = blockEnd.next;
            }
            // Неполный последний блок оставляем без изменений.
            if (blockEnd == null) {
                break;
            }

            ListNode afterBlock = blockEnd.next;
            ListNode blockStart = current;
            ListNode previous = afterBlock;
            while (current != afterBlock) {
                ListNode next = current.next;
                current.next = previous;
                previous = current;
                current = next;
            }

            if (previousTail == null) {
                head = blockEnd;
            } else {
                previousTail.next = blockEnd;
            }
            previousTail = blockStart;
        }
        return head;
    }
}
