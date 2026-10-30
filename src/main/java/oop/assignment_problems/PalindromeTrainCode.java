
public class PalindromeTrainCode {
    static class Node {
        int data; Node next;
        Node(int data) { this.data = data; }
    }

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        // 1. Find the middle (slow ends at the end of the first half).
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half.
        Node secondHalf = reverse(slow.next);

        // 3. Compare both halves.
        boolean result = true;
        Node p1 = head, p2 = secondHalf;
        while (p2 != null) {
            if (p1.data != p2.data) { result = false; break; }
            p1 = p1.next; p2 = p2.next;
        }

        // 4. Restore the list by reversing the second half again.
        slow.next = reverse(secondHalf);
        return result;
    }

    private static Node reverse(Node head) {
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
        Node a = build(1, 2, 3, 2, 1);
        System.out.println(isPalindrome(a) + "  (list: " + print(a) + ")");
        Node b = build(1, 2, 3, 2);
        System.out.println(isPalindrome(b) + "  (list: " + print(b) + ")");
        Node c = build(1, 2, 2, 1);
        System.out.println(isPalindrome(c) + "  (list: " + print(c) + ")");
        Node d = build(7);
        System.out.println(isPalindrome(d) + "  (list: " + print(d) + ")");
    }
}
