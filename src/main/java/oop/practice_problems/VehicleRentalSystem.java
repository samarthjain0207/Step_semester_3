import java.util.*;

abstract class Vehicle {
    private final String name;
    private boolean available = true;
    Vehicle(String name) { this.name = name; }
    String getName() { return name; }
    boolean isAvailable() { return available; }
    void setAvailable(boolean a) { available = a; }
    abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {
    StandardCar(String n) { super(n); }
    double calculateCharge(int days) { return 50.0 * days; }
}

class LuxuryCar extends Vehicle {
    LuxuryCar(String n) { super(n); }
    double calculateCharge(int days) { return 100.0 * days; }
}

class SUV extends Vehicle {
    SUV(String n) { super(n); }
    double calculateCharge(int days) { return 80.0 * days; }
}

class Renter {
    final String name;
    Renter(String name) { this.name = name; }
}

class Rental {
    final Renter customer;
    final Vehicle vehicle;
    final int days;
    final double charge;
    private boolean active = true;

    Rental(Renter c, Vehicle v, int days) {
        customer = c; vehicle = v; this.days = days;
        charge = v.calculateCharge(days);   // polymorphic
    }
    boolean isActive() { return active; }
    void close() { active = false; }
}

class RentalService {
    private final Map<String, Vehicle> vehicles = new HashMap<>();
    private final List<Rental> rentals = new ArrayList<>();

    void addVehicle(Vehicle v) { vehicles.put(v.getName(), v); }

    Rental rent(Renter c, String vehicleName, int days) {
        if (days <= 0) throw new IllegalArgumentException("Rental duration must be positive.");
        Vehicle v = vehicles.get(vehicleName);
        if (v == null) throw new IllegalArgumentException("Unknown vehicle: " + vehicleName);
        if (!v.isAvailable()) {
            System.out.println("Rental failed: " + vehicleName + " is already rented.");
            return null;
        }
        Rental r = new Rental(c, v, days);
        v.setAvailable(false);
        rentals.add(r);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n", vehicleName, days, r.charge);
        return r;
    }

    void returnVehicle(String vehicleName) {
        for (Rental r : rentals) {
            if (r.vehicle.getName().equals(vehicleName) && r.isActive()) {
                r.close();
                r.vehicle.setAvailable(true);
                System.out.println(vehicleName + " returned. Now available.");
                return;
            }
        }
        System.out.println("No active rental for " + vehicleName + ".");
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService svc = new RentalService();
        svc.addVehicle(new LuxuryCar("Luxury Car A"));
        svc.addVehicle(new StandardCar("Standard Car B"));
        Renter c = new Renter("Customer");

        svc.rent(c, "Luxury Car A", 3);
        svc.rent(c, "Standard Car B", 5);
        svc.rent(c, "Luxury Car A", 1);   // double-booking blocked
        svc.returnVehicle("Luxury Car A");
    }
}
