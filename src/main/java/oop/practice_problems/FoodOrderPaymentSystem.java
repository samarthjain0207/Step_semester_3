import java.util.*;

enum OrderStatus {
    CREATED("Created"), PAID("Paid"), PENDING_PAYMENT("Pending Payment");
    private final String label;
    OrderStatus(String l) { label = l; }
    String label() { return label; }
}

class FoodItem {
    final String name; final double price;
    FoodItem(String name, double price) { this.name = name; this.price = price; }
}

class Restaurant {
    private final String name;
    private final Map<String, FoodItem> menu = new LinkedHashMap<>();
    Restaurant(String name, List<FoodItem> items) {
        this.name = name;
        for (FoodItem f : items) menu.put(f.name, f);
    }
}

class LineItem {
    private final FoodItem food; private final int qty;
    LineItem(FoodItem food, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        this.food = food; this.qty = qty;
    }
    double subtotal() { return food.price * qty; }
}

// ---- payments: Order depends only on this abstraction ----
interface IPaymentMethod {
    String getName();
    boolean pay(double amount);
}

class CreditCardPayment implements IPaymentMethod {
    private final boolean success;
    CreditCardPayment(boolean success) { this.success = success; }
    public String getName() { return "Credit Card"; }
    public boolean pay(double amount) { return success; }
}

class DigitalWalletPayment implements IPaymentMethod {
    private final boolean success;
    DigitalWalletPayment(boolean success) { this.success = success; }
    public String getName() { return "Digital Wallet"; }
    public boolean pay(double amount) { return success; }
}

class CashOnDeliveryPayment implements IPaymentMethod {
    public String getName() { return "Cash on Delivery"; }
    public boolean pay(double amount) { return true; }
}

// ---- notifications (Observer) ----
interface OrderListener { void update(String message); }

class CustomerNotifier implements OrderListener {
    public void update(String message) { System.out.println("Notification: " + message); }
}

class Customer {
    final String name;
    private final List<Order> orders = new ArrayList<>();
    private final OrderListener notifier = new CustomerNotifier();
    Customer(String name) { this.name = name; }

    Order createOrder() {
        Order o = new Order(this);
        o.subscribe(notifier);
        orders.add(o);
        System.out.println("Order created.");
        return o;
    }
}

class Order {
    private static int nextId = 123;
    private final Customer customer;
    private final List<LineItem> items = new ArrayList<>();   // composition
    private final List<OrderListener> listeners = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;
    private int id;

    Order(Customer c) { customer = c; }
    void subscribe(OrderListener l) { listeners.add(l); }
    private void notifyListeners(String msg) { for (OrderListener l : listeners) l.update(msg); }

    void addItem(FoodItem food, int qty) {
        items.add(new LineItem(food, qty));
        System.out.println("Added " + food.name + " (Qty " + qty + ").");
    }

    double total() { return items.stream().mapToDouble(LineItem::subtotal).sum(); }

    boolean placeOrder(IPaymentMethod method) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return false;
        }
        id = nextId++;
        if (method.pay(total())) {
            status = OrderStatus.PAID;
            System.out.println("Order placed successfully. Payment via " + method.getName()
                    + " successful. Order status: " + status.label() + ".");
            notifyListeners("Order #" + id + " placed and paid.");
            return true;
        }
        status = OrderStatus.PENDING_PAYMENT;
        System.out.println("Order placed. Payment via " + method.getName()
                + " failed. Order status: " + status.label() + ".");
        notifyListeners("Order #" + id + " placed, awaiting payment.");
        return false;
    }
}

public class FoodOrderPaymentSystem {
    public static void main(String[] args) {
        FoodItem pizza = new FoodItem("Pizza", 8.0);
        FoodItem soda = new FoodItem("Soda", 2.0);
        FoodItem burger = new FoodItem("Burger", 6.0);
        Customer customer = new Customer("Customer");

        Order empty = customer.createOrder();
        empty.placeOrder(new CreditCardPayment(true));       // fails: empty cart

        Order o1 = customer.createOrder();
        o1.addItem(pizza, 2);
        o1.addItem(soda, 1);
        o1.placeOrder(new CreditCardPayment(true));

        Order o2 = customer.createOrder();
        o2.addItem(burger, 1);
        o2.placeOrder(new DigitalWalletPayment(false));
    }
}
