import java.io.*;
import java.util.*;
import java.util.regex.*;

public class RecentPagesCache {

    static class Node {
        int key;
        String page;
        Node prev, next;

        Node(int key, String page) {
            this.key = key;
            this.page = page;
        }
    }

    static class RecentCache {
        private final int capacity;
        private final Map<Integer, Node> map = new HashMap<>();
        private final Node head = new Node(0, null);
        private final Node tail = new Node(0, null);
        int lastEvicted;
        boolean lastUpdated;

        RecentCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        private void unlink(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }

        private void addFront(Node n) {
            n.next = head.next;
            n.prev = head;
            head.next.prev = n;
            head.next = n;
        }

        String get(int key) {
            Node n = map.get(key);
            if (n == null) return "-1";
            unlink(n);
            addFront(n);
            return n.page;
        }

        void put(int key, String page) {
            lastEvicted = -1;
            lastUpdated = false;
            Node n = map.get(key);
            if (n != null) {
                n.page = page;
                unlink(n);
                addFront(n);
                lastUpdated = true;
                return;
            }
            if (map.size() == capacity) {
                Node lru = tail.prev;
                unlink(lru);
                map.remove(lru.key);
                lastEvicted = lru.key;
            }
            Node fresh = new Node(key, page);
            map.put(key, fresh);
            addFront(fresh);
        }

        String contents() {
            StringBuilder sb = new StringBuilder();
            for (Node p = head.next; p != tail; p = p.next) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(p.key).append('=').append(p.page);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append('\n');
        String text = all.toString();

        Matcher op = Pattern.compile("(put|get)\\(\\s*(-?\\d+)\\s*(?:,\\s*([^)]*?))?\\s*\\)").matcher(text);
        List<int[]> keys = new ArrayList<>();
        List<String> kinds = new ArrayList<>();
        List<String> pages = new ArrayList<>();
        int firstOp = text.length();
        while (op.find()) {
            if (kinds.isEmpty()) firstOp = op.start();
            kinds.add(op.group(1));
            keys.add(new int[]{Integer.parseInt(op.group(2))});
            pages.add(op.group(3));
        }

        Matcher cap = Pattern.compile("\\d+").matcher(text.substring(0, firstOp));
        cap.find();
        RecentCache cache = new RecentCache(Integer.parseInt(cap.group()));

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < kinds.size(); i++) {
            int key = keys.get(i)[0];
            if (kinds.get(i).equals("get")) {
                out.append("get(").append(key).append(") -> ").append(cache.get(key)).append('\n');
            } else {
                String page = pages.get(i);
                cache.put(key, page);
                out.append("put(").append(key).append(',').append(page).append(')');
                if (cache.lastUpdated) out.append(" updated");
                else if (cache.lastEvicted != -1) out.append(" evicted ").append(cache.lastEvicted);
                out.append('\n');
            }
        }
        out.append("Cache (most recent first): ").append(cache.contents()).append('\n');
        out.append("get: O(1) time, put: O(1) time, space: O(capacity)\n");
        out.append("A singly linked list cannot remove an arbitrary node in O(1): it has no prev pointer, so finding the node before it needs an O(n) walk from the head.\n");
        System.out.print(out);
    }
}
