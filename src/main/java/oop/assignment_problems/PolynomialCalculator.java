/**
 * Problem 9: Polynomial Calculator
 *
 * Time:  O(m + n) - each input list is visited once (merge of two sorted lists).
 * Space: O(m + n) for the new result list (at most m + n terms); O(1) extra otherwise.
 *
 * Terms whose coefficients cancel to 0 are skipped, so they never appear in the result.
 */
public class PolynomialCalculator {
    static class Node {
        int coef, exp; Node next;
        Node(int coef, int exp) { this.coef = coef; this.exp = exp; }
    }

    static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0), tail = dummy;
        while (p != null || q != null) {
            int c, e;
            if (q == null || (p != null && p.exp > q.exp)) { c = p.coef; e = p.exp; p = p.next; }
            else if (p == null || q.exp > p.exp)           { c = q.coef; e = q.exp; q = q.next; }
            else { c = p.coef + q.coef; e = p.exp; p = p.next; q = q.next; } // equal exponents
            if (c != 0) { tail.next = new Node(c, e); tail = tail.next; }
        }
        return dummy.next;
    }

    /** Standard form, e.g. 5x^3 + 9x^2 - 3x + 3 (prints 0 for an empty polynomial). */
    static String print(Node head) {
        if (head == null) return "0";
        StringBuilder sb = new StringBuilder();
        for (Node t = head; t != null; t = t.next) {
            int abs = Math.abs(t.coef);
            if (t == head) { if (t.coef < 0) sb.append("-"); }
            else sb.append(t.coef < 0 ? " - " : " + ");
            if (t.exp == 0) sb.append(abs);
            else {
                if (abs != 1) sb.append(abs);
                sb.append("x");
                if (t.exp != 1) sb.append("^").append(t.exp);
            }
        }
        return sb.toString();
    }

    /** Build from alternating coef, exp pairs. */
    static Node build(int... ce) {
        Node head = null, tail = null;
        for (int i = 0; i < ce.length; i += 2) {
            Node n = new Node(ce[i], ce[i + 1]);
            if (head == null) head = tail = n; else { tail.next = n; tail = n; }
        }
        return head;
    }

    public static void main(String[] args) {
        // 5x^3 + 4x^2 + 2  and  5x^2 - 3x + 1
        System.out.println(print(addPolynomials(build(5, 3, 4, 2, 2, 0), build(5, 2, -3, 1, 1, 0))));
        // 3x^2 + 1  and  -3x^2 + 4x
        System.out.println(print(addPolynomials(build(3, 2, 1, 0), build(-3, 2, 4, 1))));
        // extras: full cancellation, empty input
        System.out.println(print(addPolynomials(build(2, 1), build(-2, 1))));
        System.out.println(print(addPolynomials(null, build(-1, 2, 1, 0))));
    }
}
