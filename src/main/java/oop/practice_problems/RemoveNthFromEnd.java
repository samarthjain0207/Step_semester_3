public class RemoveNthFromEnd {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static Node removeNthFromEnd(Node head, int n) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node fast = dummy, slow = dummy;
        for (int i = 0; i < n; i++) fast = fast.next;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    static Node build(int... vals) {
        Node head = null, tail = null;
        for (int v : vals) {
            Node node = new Node(v);
            if (head == null) head = tail = node;
            else { tail.next = node; tail = node; }
        }
        return head;
    }

    static String print(Node head) {
        if (head == null) return "(empty list)";
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(c.next != null ? " -> " : "");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(print(removeNthFromEnd(build(1, 2, 3, 4, 5), 2)));
        System.out.println(print(removeNthFromEnd(build(1), 1)));
    }
}
