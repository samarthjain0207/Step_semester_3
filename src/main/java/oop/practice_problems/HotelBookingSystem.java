import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

enum ReservationStatus { ACTIVE, CANCELLED }

interface PricingStrategy { double pricePerNight(); }

class StandardPricing implements PricingStrategy { public double pricePerNight() { return 150.0; } }
class DeluxePricing   implements PricingStrategy { public double pricePerNight() { return 200.0; } }
class SuitePricing    implements PricingStrategy { public double pricePerNight() { return 350.0; } }

class Guest {
    final String name;
    Guest(String name) { this.name = name; }
}

class Room {
    private final String name;
    private final PricingStrategy pricing;
    private final List<Reservation> reservations = new ArrayList<>();

    Room(String name, PricingStrategy pricing) { this.name = name; this.pricing = pricing; }
    String getName() { return name; }
    PricingStrategy getPricing() { return pricing; }
    void addReservation(Reservation r) { reservations.add(r); }

    // overlap on half-open range [start, end)
    boolean isAvailable(LocalDate start, LocalDate end) {
        for (Reservation r : reservations)
            if (r.getStatus() == ReservationStatus.ACTIVE
                    && start.isBefore(r.getEnd()) && end.isAfter(r.getStart()))
                return false;
        return true;
    }
}

class Reservation {
    private final Guest customer;
    private final Room room;
    private final LocalDate start, end, cancelDeadline;
    private final double totalPrice;
    private ReservationStatus status = ReservationStatus.ACTIVE;

    Reservation(Guest c, Room room, LocalDate start, LocalDate end) {
        this.customer = c; this.room = room; this.start = start; this.end = end;
        this.cancelDeadline = start.minusDays(2);
        this.totalPrice = room.getPricing().pricePerNight() * ChronoUnit.DAYS.between(start, end);
    }
    Room getRoom() { return room; }
    LocalDate getStart() { return start; }
    LocalDate getEnd() { return end; }
    LocalDate getCancelDeadline() { return cancelDeadline; }
    double getTotalPrice() { return totalPrice; }
    ReservationStatus getStatus() { return status; }
    void cancel() { status = ReservationStatus.CANCELLED; }
}

class Hotel {
    private final Map<String, Room> rooms = new HashMap<>();
    private final List<Reservation> reservations = new ArrayList<>();

    void addRoom(Room r) { rooms.put(r.getName(), r); }

    Reservation book(Guest c, String roomName, LocalDate start, LocalDate end) {
        if (!end.isAfter(start)) throw new IllegalArgumentException("Check-out must be after check-in.");
        Room room = rooms.get(roomName);
        if (room == null) throw new IllegalArgumentException("Unknown room: " + roomName);
        if (!room.isAvailable(start, end)) {
            System.out.println("Booking failed: " + roomName + " is not available for " + start + " to " + end + ".");
            return null;
        }
        Reservation res = new Reservation(c, room, start, end);
        room.addReservation(res);
        reservations.add(res);
        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n", roomName, start, end, res.getTotalPrice());
        return res;
    }

    void cancel(Reservation res, LocalDate today) {
        if (res.getStatus() == ReservationStatus.CANCELLED) {
            System.out.println("Cancellation failed: already cancelled.");
        } else if (today.isAfter(res.getCancelDeadline())) {
            System.out.println("Cancellation failed: deadline " + res.getCancelDeadline() + " has passed.");
        } else {
            res.cancel();
            System.out.println("Reservation for " + res.getRoom().getName() + " cancelled successfully.");
        }
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.addRoom(new Room("Deluxe Room 101", new DeluxePricing()));
        hotel.addRoom(new Room("Standard Room 205", new StandardPricing()));
        Guest c = new Guest("Customer");

        Reservation r1 = hotel.book(c, "Deluxe Room 101", LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        hotel.book(c, "Standard Room 205", LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        hotel.book(c, "Deluxe Room 101", LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7)); // overlap
        hotel.cancel(r1, LocalDate.of(2024, 11, 20));
    }
}
