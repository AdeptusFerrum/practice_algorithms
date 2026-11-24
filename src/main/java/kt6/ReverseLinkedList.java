package kt6;
public class ReverseLinkedList {
    public static ListNode reverse(ListNode head) {
        ListNode previous = null;
        ListNode current = head;
        while (current != null) {
            // Сохраняем следующий узел до разворота ссылки, чтобы не потерять остаток списка.
            ListNode next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }
}
