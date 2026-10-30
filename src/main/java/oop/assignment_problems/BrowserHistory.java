/**
 * Problem 6: Campus Portal Browser History (own doubly linked list)
 *
 * Costs:  visit   O(1)
 *         back    O(steps)
 *         forward O(steps)
 * Space:  O(n) for n pages currently held in the list (one node per page).
 *
 * visit(url): the new node is linked after `current` and current.next is
 * overwritten, which cuts off the whole forward chain in O(1). The old forward
 * nodes are no longer reachable from the list, so Java's garbage collector
 * reclaims them (we also null the old next.prev to avoid a dangling back-link).
 */
public class BrowserHistory {
    static class Node {
        String data; Node prev, next;
        Node(String data) { this.data = data; }
    }

    private Node current;

    BrowserHistory(String homepage) { current = new Node(homepage); }

    void visit(String url) {
        Node n = new Node(url);
        if (current.next != null) current.next.prev = null; // detach forward history
        current.next = n;                                    // drops everything that followed
        n.prev = current;
        current = n;
    }

    String back(int steps) {
        while (steps-- > 0 && current.prev != null) current = current.prev;
        return current.data;
    }

    String forward(int steps) {
        while (steps-- > 0 && current.next != null) current = current.next;
        return current.data;
    }

    public static void main(String[] args) {
        BrowserHistory b = new BrowserHistory("srm.edu");
        b.visit("a.com"); b.visit("b.com"); b.visit("c.com");
        System.out.println(String.join(", ",
            b.back(1), b.back(1), b.forward(1),
            visitThen(b, "d.com", 2), // forward 2 after visiting d.com
            b.back(2), b.back(7)));
    }

    private static String visitThen(BrowserHistory b, String url, int fwd) {
        b.visit(url);
        return b.forward(fwd);
    }
}
