import java.util.*;

public class CustomerRegistry {
    static final class Customer {
        private final int id; private final String name;
        Customer(int id, String name) { this.id = id; this.name = name; }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Customer)) return false;
            Customer c = (Customer) o;
            return id == c.id && name.equals(c.name);
        }
        @Override public int hashCode() { return Objects.hash(id, name); }
        @Override public String toString() { return "(" + id + ", " + name + ")"; }
    }

    private final Set<Customer> customers = new HashSet<>();
    boolean add(Customer c) { return customers.add(c); }
    boolean contains(Customer c) { return customers.contains(c); }
    int uniqueCount() { return customers.size(); }

    public static void main(String[] args) {
        CustomerRegistry r = new CustomerRegistry();
        System.out.println(r.add(new Customer(101, "Asha")));                       // Branch A
        System.out.println(r.add(new Customer(101, "Asha")) + " (duplicate rejected)"); // Branch B
        System.out.println(r.add(new Customer(102, "Ravi")));
        System.out.println("unique count " + r.uniqueCount());
        System.out.println("contains: " + r.contains(new Customer(101, "Asha")));
    }
}
