import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DeliveryRouteLoopDetector {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static String detectLoop(Node head) {
        Node slow = head, fast = head;
        boolean meet = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                meet = true;
                break;
            }
        }
        if (!meet) return "Loop: no";

        int length = 1;
        for (Node p = slow.next; p != slow; p = p.next) length++;

        Node a = head, b = slow;
        while (a != b) {
            a = a.next;
            b = b.next;
        }
        return "Loop: yes, starts at stop " + a.data + ", length " + length;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append('\n');

        List<Integer> nums = new ArrayList<>();
        Matcher m = Pattern.compile("-?\\d+").matcher(all);
        while (m.find()) nums.add(Integer.parseInt(m.group()));

        int pos = nums.remove(nums.size() - 1);
        int n = nums.size();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(nums.get(i));
            if (i > 0) nodes[i - 1].next = nodes[i];
        }
        if (pos >= 0) nodes[n - 1].next = nodes[pos];

        System.out.println(detectLoop(nodes[0]));
        System.out.println("Time: O(n)");
        System.out.println("Additional space: O(1)");
        System.out.println("Why they meet: once both pointers are inside the loop, the fast pointer gains one step on the slow pointer each move.");
        System.out.println("The gap to the slow pointer shrinks by one each time, so it must reach 0 and they meet inside the loop.");
    }
}
