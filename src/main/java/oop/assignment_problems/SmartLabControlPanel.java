import java.util.*;

// Every capability is an object; Device HAS capabilities (composition)
interface Capability {
    String getName();
    // Power: 1 = ON, 0 = OFF. Throws IllegalArgumentException (state untouched) if invalid.
    String apply(int value);
}

class PowerCapability implements Capability {
    private boolean on;
    public String getName() { return "Power"; }
    public String apply(int value) {
        if (value != 0 && value != 1) throw new IllegalArgumentException("power must be 0 (OFF) or 1 (ON).");
        on = (value == 1);
        return on ? "ON" : "OFF";
    }
}

abstract class RangedCapability implements Capability {
    private final String name, unit;
    private final int min, max;
    private int value;
    RangedCapability(String name, int min, int max, String unit) {
        this.name = name; this.min = min; this.max = max; this.unit = unit; this.value = min;
    }
    public String getName() { return name; }
    public String apply(int v) {
        if (v < min || v > max)   // validate BEFORE changing state
            throw new IllegalArgumentException(name.toLowerCase() + " must be between " + min + unit + " and " + max + unit + ".");
        value = v;
        return name.toLowerCase() + " set to " + v + unit;
    }
}

class BrightnessCapability extends RangedCapability {
    BrightnessCapability() { super("Brightness", 0, 100, "%"); }
}

class TemperatureCapability extends RangedCapability {
    TemperatureCapability() { super("Temperature", 16, 30, "\u00B0C"); }
}

class Device {
    private final String name;
    private final Map<String, Capability> capabilities = new LinkedHashMap<>();

    Device(String name, Capability... initial) {
        this.name = name;
        for (Capability c : initial) capabilities.put(c.getName().toLowerCase(), c);
    }

    String getName() { return name; }
    boolean supports(String cap) { return capabilities.containsKey(cap.toLowerCase()); }

    // runtime addition
    void addCapability(Capability c) {
        String key = c.getName().toLowerCase();
        if (capabilities.containsKey(key)) {
            System.out.println(name + ": " + c.getName() + " capability already present."); return;
        }
        capabilities.put(key, c);
        System.out.println(name + ": " + c.getName() + " capability added.");
    }

    boolean command(String cap, int value) {
        Capability c = capabilities.get(cap.toLowerCase());
        if (c == null) {
            System.out.println("Rejected: " + name + " does not support " + cap + "."); return false;
        }
        try {
            System.out.println(name + ": " + c.apply(value) + ".");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + name + " " + e.getMessage());
            return false;
        }
    }
}

class SceneStep {
    final String capability; final int value;
    SceneStep(String capability, int value) { this.capability = capability; this.value = value; }
}

class Scene {
    private final String name;
    private final List<SceneStep> steps = new ArrayList<>();
    Scene(String name) { this.name = name; }
    Scene step(String capability, int value) { steps.add(new SceneStep(capability, value)); return this; }
    String getName() { return name; }
    List<SceneStep> getSteps() { return steps; }
}

class LabController {
    private final List<Device> devices = new ArrayList<>();
    void addDevice(Device d) { devices.add(d); }

    void runScene(Scene scene) {
        System.out.println("Scene '" + scene.getName() + "' started.");
        int applied = 0;
        for (SceneStep step : scene.getSteps())
            for (Device d : devices)
                if (d.supports(step.capability) && d.command(step.capability, step.value)) applied++;
        System.out.println("Scene '" + scene.getName() + "' completed: " + applied + " actions applied.");
    }
}

public class SmartLabControlPanel {
    public static void main(String[] args) {
        Device ac = new Device("Lab AC", new PowerCapability(), new TemperatureCapability());
        Device lights = new Device("Ceiling Lights", new PowerCapability(), new BrightnessCapability());
        Device projector = new Device("Projector", new PowerCapability());

        LabController lab = new LabController();
        lab.addDevice(ac); lab.addDevice(lights); lab.addDevice(projector);

        Scene lecture = new Scene("Lecture Mode")
                .step("Power", 1).step("Brightness", 40).step("Temperature", 24);
        lab.runScene(lecture);

        ac.command("Temperature", 12);                      // rejected, AC unchanged
        projector.addCapability(new BrightnessCapability());
        projector.command("Brightness", 70);
    }
}
