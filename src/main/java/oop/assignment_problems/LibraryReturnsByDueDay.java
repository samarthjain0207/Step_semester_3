import java.io.*;
import java.util.*;
import java.util.regex.*;

public class LibraryReturnsByDueDay {

    static class Node {
        String title;
        int day;
        Node next;

        Node(String title, int day) {
            this.title = title;
            this.day = day;
        }
    }

    static Node sortByDueDay(Node head) {
        if (head == null || head.next == null) return head;

        Node slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node right = slow.next;
        slow.next = null;

        return merge(sortByDueDay(head), sortByDueDay(right));
    }

    static Node merge(Node a, Node b) {
        Node dummy = new Node("", 0);
        Node tail = dummy;
        while (a != null && b != null) {
            if (a.day <= b.day) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append('\n');

        Node head = null, tail = null;
        Matcher m = Pattern.compile("([\\w.]+):(\\d+)").matcher(all);
        while (m.find()) {
            Node nd = new Node(m.group(1), Integer.parseInt(m.group(2)));
            if (head == null) head = nd;
            else tail.next = nd;
            tail = nd;
        }

        head = sortByDueDay(head);

        StringBuilder out = new StringBuilder();
        for (Node p = head; p != null; p = p.next) out.append(p.title).append(':').append(p.day).append(" -> ");
        out.append("null");
        System.out.println(out);
        System.out.println("Time: O(n log n)");
        System.out.println("Additional space: O(log n) for the recursion stack, no extra arrays or copies; the merge itself is O(1) space.");
        System.out.println("Why merge sort suits linked lists: merging only relinks nodes and needs no random access, while quick sort needs index access and swaps and can degrade to O(n^2).");
    }
}
