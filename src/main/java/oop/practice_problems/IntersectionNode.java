public class IntersectionNode {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static int length(Node head) {
        int len = 0;
        for (Node c = head; c != null; c = c.next) len++;
        return len;
    }

    static Node findIntersection(Node a, Node b) {
        int la = length(a), lb = length(b);
        while (la > lb) { a = a.next; la--; }
        while (lb > la) { b = b.next; lb--; }
        while (a != null && a != b) {
            a = a.next;
            b = b.next;
        }
        return a;
    }

    static Node build(Node tailNext, int... vals) {
        Node head = null, tail = null;
        for (int v : vals) {
            Node node = new Node(v);
            if (head == null) head = tail = node;
            else { tail.next = node; tail = node; }
        }
        if (tail != null) tail.next = tailNext;
        return head;
    }

    public static void main(String[] args) {
        Node shared = build(null, 8, 4, 5);
        Node a = build(shared, 4, 1);
        Node b = build(shared, 5, 6, 1);
        Node hit = findIntersection(a, b);
        System.out.println(hit == null ? "null" : "Node with value " + hit.data);

        Node c = build(null, 2, 6, 4);
        Node d = build(null, 1, 5);
        Node miss = findIntersection(c, d);
        System.out.println(miss == null ? "null" : "Node with value " + miss.data);
    }
}
