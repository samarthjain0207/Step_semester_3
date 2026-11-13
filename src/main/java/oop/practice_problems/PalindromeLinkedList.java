public class PalindromeLinkedList {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node second = reverse(slow.next);
        boolean result = true;
        Node p = head, q = second;
        while (q != null) {
            if (p.data != q.data) { result = false; break; }
            p = p.next;
            q = q.next;
        }
        slow.next = reverse(second);
        return result;
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

    public static void main(String[] args) {
        System.out.println(isPalindrome(build(1, 2, 2, 1)));
        System.out.println(isPalindrome(build(1, 2, 3)));
    }
}
