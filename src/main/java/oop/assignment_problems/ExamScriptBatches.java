import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ExamScriptBatches {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node reverseInGroups(Node head, int k) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node prevTail = dummy;

        while (true) {
            Node check = prevTail.next;
            int count = 0;
            while (check != null && count < k) {
                check = check.next;
                count++;
            }
            if (count < k) break;

            Node groupHead = prevTail.next;
            Node prev = check;
            Node cur = groupHead;
            for (int i = 0; i < k; i++) {
                Node nxt = cur.next;
                cur.next = prev;
                prev = cur;
                cur = nxt;
            }
            prevTail.next = prev;
            prevTail = groupHead;
        }
        return dummy.next;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append('\n');

        List<Integer> nums = new ArrayList<>();
        Matcher m = Pattern.compile("-?\\d+").matcher(all);
        while (m.find()) nums.add(Integer.parseInt(m.group()));

        int k = nums.remove(nums.size() - 1);
        Node head = null, tail = null;
        for (int v : nums) {
            Node nd = new Node(v);
            if (head == null) head = nd;
            else tail.next = nd;
            tail = nd;
        }

        head = reverseInGroups(head, k);

        StringBuilder out = new StringBuilder();
        for (Node p = head; p != null; p = p.next) out.append(p.data).append(" -> ");
        out.append("null");
        System.out.println(out);
        System.out.println("Time: O(n)");
        System.out.println("Additional space: O(1)");
        System.out.println("No recursion: each group is reversed with a loop and a dummy node, so there is no O(n/k) stack.");
    }
}
