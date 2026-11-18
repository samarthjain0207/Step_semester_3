import java.io.*;
import java.util.*;

public class OrderWalkthrough {

    static class Order {

        static class LineItem {
            final String productName;
            final int quantity;
            final double unitPrice;

            LineItem(String productName, int quantity, double unitPrice) {
                this.productName = productName;
                this.quantity = quantity;
                this.unitPrice = unitPrice;
            }

            double total() {
                return quantity * unitPrice;
            }
        }

        private LineItem[] items = new LineItem[4];
        private int size = 0;

        void add(String name, int qty, double price) {
            if (size == items.length) {
                items = Arrays.copyOf(items, size * 2);
            }
            items[size++] = new LineItem(name, qty, price);
        }

        Cursor cursor() {
            return new Cursor();
        }

        class Cursor {
            private int pos = 0;

            boolean hasNext() {
                return pos < size;
            }

            LineItem next() {
                return items[pos++];
            }

            void reset() {
                pos = 0;
            }
        }
    }

    static String fmt(double d) {
        if (d == Math.rint(d)) return String.valueOf((long) d);
        return String.format("%.2f", d);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Order order = new Order();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            List<String> t = new ArrayList<>();
            for (String s : line.split("\\s+")) {
                if (!s.equalsIgnoreCase("x")) t.add(s);
            }
            if (t.size() < 3) continue;
            order.add(t.get(0), Integer.parseInt(t.get(1)), Double.parseDouble(t.get(2)));
        }

        double grand = 0;
        Order.Cursor c = order.cursor();
        while (c.hasNext()) {
            Order.LineItem li = c.next();
            System.out.println(li.productName + " " + fmt(li.total()));
            grand += li.total();
        }
        System.out.println("Total " + fmt(grand));
        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
        System.out.println("add item: O(1) amortised time, O(n) space");
        System.out.println("walk order: O(n) time, O(1) extra space");
    }
}
