package kt6;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ReverseKGroupTest {
    @Test
    void reversesFullBlocksAndKeepsLastNode() {
        ListNode[] nodes = nodes(1, 2, 3, 4, 5);
        assertOrder(ReverseKGroup.reverse(nodes[0], 2), nodes[1], nodes[0], nodes[3], nodes[2], nodes[4]);
    }

    @Test
    void keepsIncompleteBlockOfSeveralNodes() {
        ListNode[] nodes = nodes(1, 2, 3, 4, 5);
        assertOrder(ReverseKGroup.reverse(nodes[0], 3), nodes[2], nodes[1], nodes[0], nodes[3], nodes[4]);
    }

    @Test
    void reversesAllCompleteBlocks() {
        ListNode[] nodes = nodes(1, 2, 3, 4, 5, 6);
        assertOrder(ReverseKGroup.reverse(nodes[0], 3),
                nodes[2], nodes[1], nodes[0], nodes[5], nodes[4], nodes[3]);
    }

    @Test
    void blockOfOneKeepsOriginalLinks() {
        ListNode[] nodes = nodes(1, 2, 3);
        assertOrder(ReverseKGroup.reverse(nodes[0], 1), nodes);
    }

    @Test
    void blockLargerThanListKeepsOriginalLinks() {
        ListNode[] nodes = nodes(1, 2, 3);
        assertOrder(ReverseKGroup.reverse(nodes[0], 200_000), nodes);
        ListNode single = new ListNode(7);
        assertOrder(ReverseKGroup.reverse(single, 2), single);
    }

    @Test
    void reversesWholeListWithoutReplacingNodes() {
        ListNode[] nodes = nodes(-1_000_000_000, 0, 0, 1_000_000_000);
        assertOrder(ReverseKGroup.reverse(nodes[0], 4), nodes[3], nodes[2], nodes[1], nodes[0]);
    }

    @Test
    void handlesMaximumLengthAndBlockSize() {
        ListNode[] nodes = nodes(IntStream.range(0, 200_000).toArray());
        ListNode current = ReverseKGroup.reverse(nodes[0], 200_000);
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
