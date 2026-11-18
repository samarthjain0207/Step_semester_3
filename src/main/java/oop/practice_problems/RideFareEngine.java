import java.io.*;
import java.util.*;

public class RideFareEngine {

    static abstract class Ride {
        protected final double distance;

        Ride(double distance) {
            this.distance = distance;
        }

        abstract double calculateFare();
    }

    static class Auto extends Ride {
        Auto(double d) { super(d); }
        double calculateFare() { return 30 + 15 * distance; }
    }

    static class Cab extends Ride {
        private final double surge;
        Cab(double d, double surge) { super(d); this.surge = surge; }
        double calculateFare() { return (50 + 20 * distance) * surge; }
    }

    static class Bike extends Ride {
        private final double waiting;
        Bike(double d, double waiting) { super(d); this.waiting = waiting; }
        double calculateFare() { return 20 + 10 * distance + 2 * waiting; }
    }

    static String fmt(double d) {
        if (d == Math.rint(d)) return String.valueOf((long) d);
        return String.format("%.2f", d);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Ride> list = new ArrayList<>();
        List<String> types = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] t = line.split("\\s+");
            if (t.length < 2) continue;
            String type = t[0].toLowerCase();
            double dist = Double.parseDouble(t[1]);
            double surge = 1.0, waiting = 0;
            for (int i = 2; i < t.length - 1; i++) {
                if (t[i].equalsIgnoreCase("surge")) surge = Double.parseDouble(t[i + 1]);
                if (t[i].equalsIgnoreCase("waiting")) waiting = Double.parseDouble(t[i + 1]);
            }
            switch (type) {
                case "auto": list.add(new Auto(dist)); types.add("Auto"); break;
                case "cab": list.add(new Cab(dist, surge)); types.add("Cab"); break;
                case "bike": list.add(new Bike(dist, waiting)); types.add("Bike"); break;
                default: break;
            }
        }

        Ride[] rides = list.toArray(new Ride[0]);
        double total = 0;
        for (int i = 0; i < rides.length; i++) {
            double fare = rides[i].calculateFare();
            System.out.println(types.get(i) + ": " + fmt(fare));
            total += fare;
        }
        System.out.println("Total: " + fmt(total));
        System.out.println("Time complexity of pricing all rides: O(n)");
    }
}
