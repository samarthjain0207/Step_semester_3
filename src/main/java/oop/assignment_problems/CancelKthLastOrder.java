/**
 * Problem 5: Cancelling the k-th Last Order
 *
 * Time:  O(n) - a single pass; the lead pointer visits each node once.
 * Space: O(1) - only a dummy node and two pointers.
 *
 * Head removal (k == length) needs no special case: the trailing pointer
 * starts at a dummy node placed before head, so it ends up pointing at the
 * dummy, and dummy.next = dummy.next.next simply drops the old head.
 * Returning dummy.next then gives the new head.
 */
public class CancelKthLastOrder {
    static class Node {
        int data; Node next;
        Node(int data) { this.data = data; }
    }

    static Node removeKthFromEnd(Node head, int k) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node lead = dummy, trail = dummy;

        for (int i = 0; i < k + 1; i++) lead = lead.next;   // lead is k + 1 steps ahead
        while (lead != null) { lead = lead.next; trail = trail.next; }

        trail.next = trail.next.next;                        // unlink the target node
        return dummy.next;
    }

    static Node build(int... vals) {
        Node head = null, tail = null;
        for (int v : vals) {
            Node n = new Node(v);
            if (head == null) head = tail = n; else { tail.next = n; tail = n; }
        }
        return head;
    }

    static String print(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(c.next != null ? " -> " : "");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(print(removeKthFromEnd(build(10, 20, 30, 40, 50), 2))); // 10 -> 20 -> 30 -> 50
        System.out.println(print(removeKthFromEnd(build(10, 20, 30, 40, 50), 5))); // 20 -> 30 -> 40 -> 50
        System.out.println(print(removeKthFromEnd(build(7), 1)));                  // (empty list)
    }
}
