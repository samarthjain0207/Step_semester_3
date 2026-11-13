public class AddTwoNumbers {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static Node addNumbers(Node a, Node b) {
        Node dummy = new Node(0), tail = dummy;
        int carry = 0;
        while (a != null || b != null || carry != 0) {
            int sum = carry;
            if (a != null) { sum += a.data; a = a.next; }
            if (b != null) { sum += b.data; b = b.next; }
            tail.next = new Node(sum % 10);
            tail = tail.next;
            carry = sum / 10;
        }
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
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(c.next != null ? " -> " : "");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(print(addNumbers(build(2, 4, 3), build(5, 6, 4))));
        System.out.println(print(addNumbers(build(9, 9), build(1))));
    }
}
