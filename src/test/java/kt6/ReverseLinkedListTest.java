package kt6;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ReverseLinkedListTest {
    @Test
    void reversesLinksBetweenOriginalNodes() {
        ListNode[] nodes = nodes(1, 2, 3, 4, 5);
        assertOrder(ReverseLinkedList.reverse(nodes[0]), nodes[4], nodes[3], nodes[2], nodes[1], nodes[0]);
    }

    @Test
    void singleNode() {
        ListNode node = new ListNode(7);
        assertOrder(ReverseLinkedList.reverse(node), node);
    }

    @Test
    void twoNodes() {
        ListNode[] nodes = nodes(1, 2);
        assertOrder(ReverseLinkedList.reverse(nodes[0]), nodes[1], nodes[0]);
    }

    @Test
    void handlesRepeatedValuesAndValueLimits() {
        ListNode[] nodes = nodes(-1_000_000_000, 0, 0, 1_000_000_000);
        assertOrder(ReverseLinkedList.reverse(nodes[0]), nodes[3], nodes[2], nodes[1], nodes[0]);
    }

    @Test
    void reversingTwiceRestoresOriginalLinks() {
        ListNode[] nodes = nodes(3, 1, 3, 2);
        ListNode head = ReverseLinkedList.reverse(nodes[0]);
        assertOrder(ReverseLinkedList.reverse(head), nodes);
    }

    @Test
    void handlesMaximumLengthWithoutRecursion() {
        ListNode[] nodes = nodes(IntStream.range(0, 200_000).toArray());
        ListNode current = ReverseLinkedList.reverse(nodes[0]);
        for (int i = nodes.length - 1; i >= 0; i--) {
            assertSame(nodes[i], current);
            current = current.next;
        }
        assertNull(current);
    }

    private ListNode[] nodes(int... values) {
        ListNode[] nodes = new ListNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new ListNode(values[i]);
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
            }
        }
        return nodes;
    }

    private void assertOrder(ListNode head, ListNode... expected) {
        for (ListNode node : expected) {
            assertSame(node, head);
            head = head.next;
        }
        assertNull(head);
    }
}
