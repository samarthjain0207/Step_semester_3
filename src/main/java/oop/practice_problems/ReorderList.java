public class ReorderList {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static void reorderList(Node head) {
        if (head == null || head.next == null) return;
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node second = reverse(slow.next);
        slow.next = null;
        Node first = head;
        while (second != null) {
            Node t1 = first.next, t2 = second.next;
            first.next = second;
            second.next = t1;
            first = t1;
            second = t2;
        }
    }

    static Node reverse(Node head) {
        Node prev = null;
        while (head != null) {
            Node next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
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
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(c.next != null ? " -> " : "");
        return sb.toString();
    }

    public static void main(String[] args) {
        Node a = build(1, 2, 3, 4);
        reorderList(a);
        System.out.println(print(a));
        Node b = build(1, 2, 3, 4, 5);
        reorderList(b);
        System.out.println(print(b));
    }
}
