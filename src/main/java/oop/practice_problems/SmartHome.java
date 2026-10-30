import java.util.*;

public class SmartHome {
    interface Dimmable { String dim(int level); }
    interface Schedulable { String schedule(String time); }
    interface EnergyMonitor { String energy(); }

    static abstract class Device {
        final String id; boolean on;
        Device(String id) { this.id = id; }
        String turnOn() { on = true; return id + " is ON"; }
        String turnOff() { on = false; return id + " is OFF"; }
    }
    static class Light extends Device implements Dimmable, Schedulable {
        Light(String id) { super(id); }
        public String dim(int l) { return id + " dimmed to " + l; }
        public String schedule(String t) { return id + " scheduled " + t; }
    }
    static class Fan extends Device implements Schedulable {
        Fan(String id) { super(id); }
        public String schedule(String t) { return id + " scheduled " + t; }
    }
    static class Plug extends Device implements EnergyMonitor {
        final int kwh;
        Plug(String id, int kwh) { super(id); this.kwh = kwh; }
        public String energy() { return id + " energy " + kwh + " kWh"; }
    }

    private final Map<String, Device> devices = new LinkedHashMap<>();
    void add(Device d) { devices.put(d.id, d); }

    String process(String cmd) {
        String[] p = cmd.trim().split("\\s+");
        Device d = devices.get(p[1]);
        if (d == null) return p[1] + " not found";
        switch (p[0]) {
            case "ON": return d.turnOn();
            case "OFF": return d.turnOff();
            case "DIM": return d instanceof Dimmable ? ((Dimmable) d).dim(Integer.parseInt(p[2])) : d.id + " rejected: DIM unsupported";
            case "SCHEDULE": return d instanceof Schedulable ? ((Schedulable) d).schedule(p[2]) : d.id + " rejected: SCHEDULE unsupported";
            case "ENERGY": return d instanceof EnergyMonitor ? ((EnergyMonitor) d).energy() : d.id + " rejected: ENERGY unsupported";
            default: return "Unknown command: " + p[0];
        }
    }

    public static void main(String[] args) {
        SmartHome h = new SmartHome();
        h.add(new Light("L1")); h.add(new Fan("F1")); h.add(new Plug("P1", 12));
        for (String c : new String[]{"ON L1", "DIM L1 40", "DIM F1 30", "SCHEDULE F1 22:00", "ENERGY P1", "ENERGY L1"})
            System.out.println(h.process(c));
    }
}
