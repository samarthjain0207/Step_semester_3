import java.util.*;

enum ParcelStatus {
    BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED;

    // the only legal forward move; null = terminal
    ParcelStatus next() {
        switch (this) {
            case BOOKED:           return PICKED_UP;
            case PICKED_UP:        return IN_TRANSIT;
            case IN_TRANSIT:       return OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY: return DELIVERED;
            default:               return null;
        }
    }
}

// ---- shipping types ----
interface ShippingType {
    String getName();
    double charge(double weightKg);
}

class StandardShipping implements ShippingType {
    public String getName() { return "Standard"; }
    public double charge(double kg) { return 40 + 10 * kg; }
}

class ExpressShipping implements ShippingType {
    public String getName() { return "Express"; }
    public double charge(double kg) { return 80 + 15 * kg; }
}

// Fragile REUSES Standard through composition (has-a), not inheritance
class FragileShipping implements ShippingType {
    private final ShippingType base = new StandardShipping();
    public String getName() { return "Fragile"; }
    public double charge(double kg) { return base.charge(kg) + 50; }
}

// ---- notification channels ----
interface NotificationChannel {
    void send(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    public void send(String id, ParcelStatus s) { System.out.println("[SMS] " + id + " is now " + s + "."); }
}

class EmailChannel implements NotificationChannel {
    public void send(String id, ParcelStatus s) { System.out.println("[Email] " + id + " is now " + s + "."); }
}

class Customer {
    private final String name;
    private final List<NotificationChannel> channels = new ArrayList<>();
    Customer(String name) { this.name = name; }
    String getName() { return name; }
    void subscribe(NotificationChannel c) { channels.add(c); }
    List<NotificationChannel> getChannels() { return channels; }
}

class Parcel {
    private final String id;
    private final double weightKg;
    private final ShippingType type;
    private final Customer customer;
    private final List<NotificationChannel> channels;
    private final double charge;
    private ParcelStatus status = ParcelStatus.BOOKED;

    Parcel(String id, double weightKg, ShippingType type, Customer customer) {
        this.id = id; this.weightKg = weightKg; this.type = type; this.customer = customer;
        this.channels = new ArrayList<>(customer.getChannels());
        this.charge = type.charge(weightKg);   // polymorphic
    }

    String getId() { return id; }
    double getCharge() { return charge; }
    ShippingType getType() { return type; }
    ParcelStatus getStatus() { return status; }
    String weightLabel() {
        return (weightKg == Math.floor(weightKg) ? String.valueOf((long) weightKg) : String.valueOf(weightKg)) + " kg";
    }

    void announce() { for (NotificationChannel c : channels) c.send(id, status); }

    // all transition rules live here; no outside code can set a status
    boolean moveTo(ParcelStatus target) {
        if (target != status.next()) {
            System.out.println("Invalid transition: " + status + " \u2192 " + target + " is not allowed.");
            return false;
        }
        status = target;
        announce();
        return true;
    }

    boolean cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + id + " can be cancelled only while BOOKED.");
            return false;
        }
        status = ParcelStatus.CANCELLED;
        announce();
        return true;
    }
}

class ParcelService {
    private final Map<String, Parcel> parcels = new HashMap<>();

    Parcel book(Customer c, String id, double kg, ShippingType type) {
        if (kg <= 0) throw new IllegalArgumentException("Weight must be positive.");
        if (parcels.containsKey(id)) throw new IllegalArgumentException("Duplicate parcel id.");
        Parcel p = new Parcel(id, kg, type, c);
        parcels.put(id, p);
        System.out.println("Parcel " + id + " booked (" + type.getName() + ", " + p.weightLabel() + "). Charge: \u20B9"
                + String.format(Locale.US, "%.2f", p.getCharge()) + ".");
        p.announce();
        return p;
    }

    boolean updateStatus(String id, ParcelStatus target) { return find(id).moveTo(target); }
    boolean cancel(String id) { return find(id).cancel(); }

    private Parcel find(String id) {
        Parcel p = parcels.get(id);
        if (p == null) throw new IllegalArgumentException("Unknown parcel " + id);
        return p;
    }
}

public class SwiftShipParcelTracker {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();
        Customer customer = new Customer("Customer");
        customer.subscribe(new SmsChannel());
        customer.subscribe(new EmailChannel());

        service.book(customer, "P101", 2, new ExpressShipping());
        service.updateStatus("P101", ParcelStatus.PICKED_UP);
        service.cancel("P101");                                        // fails
        service.updateStatus("P101", ParcelStatus.IN_TRANSIT);
        service.updateStatus("P101", ParcelStatus.DELIVERED);          // invalid
    }
}
