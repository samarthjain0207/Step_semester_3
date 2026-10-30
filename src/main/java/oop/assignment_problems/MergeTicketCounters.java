
public class MergeTicketCounters {
    static class Node {
        int data; Node next;
        Node(int data) { this.data = data; }
    }

    static Node mergeSorted(Node a, Node b) {
        Node dummy = new Node(0), tail = dummy;
        while (a != null && b != null) {
            if (a.data <= b.data) { tail.next = a; a = a.next; }
            else                  { tail.next = b; b = b.next; }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;   // attach the remainder of the non-empty list
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
        System.out.println(print(mergeSorted(build(3, 8, 15), build(5, 8, 12, 20)))); // 3 -> 5 -> 8 -> 8 -> 12 -> 15 -> 20
        System.out.println(print(mergeSorted(build(), build(4))));                    // 4
        System.out.println("[" + print(mergeSorted(build(), build())) + "]");         // [] (both empty)
    }
}
