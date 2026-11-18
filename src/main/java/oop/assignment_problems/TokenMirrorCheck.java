import java.io.*;
import java.util.*;
import java.util.regex.*;

public class TokenMirrorCheck {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node reverse(Node head) {
        Node prev = null;
        while (head != null) {
            Node nxt = head.next;
            head.next = prev;
            prev = head;
            head = nxt;
        }
        return prev;
    }

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node secondHead = reverse(slow.next);
        Node a = head, b = secondHead;
        boolean result = true;
        while (b != null) {
            if (a.data != b.data) {
                result = false;
                break;
            }
            a = a.next;
            b = b.next;
        }

        slow.next = reverse(secondHead);
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append('\n');

        Node head = null, tail = null;
        Matcher m = Pattern.compile("-?\\d+").matcher(all);
        while (m.find()) {
            Node nd = new Node(Integer.parseInt(m.group()));
            if (head == null) head = nd;
            else tail.next = nd;
            tail = nd;
        }

        System.out.println("Palindrome: " + isPalindrome(head));

        StringBuilder out = new StringBuilder("List after check: ");
        for (Node p = head; p != null; p = p.next) out.append(p.data).append(" -> ");
        out.append("null");
        System.out.println(out);
        System.out.println("Time: O(n)");
        System.out.println("Additional space: O(1)");
        System.out.println("Copying values into an array or a stack needs O(n) extra space, which breaks the O(1) requirement.");
    }
}
