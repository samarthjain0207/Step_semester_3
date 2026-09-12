import java.util.ArrayList;
import java.util.List;

class PatientVitals {
    private List<Double> readings = new ArrayList<>();

    PatientVitals(double[] initialReadings) {
        for (double r : initialReadings) {
            recordReading(r);
        }
    }

    void recordReading(double reading) {
        if (reading <= 0 || reading > 45) {
            return; // silently reject
        }
        readings.add(reading);
    }

    double getAverage() {
        if (readings.isEmpty()) return 0.0;
        double sum = 0;
        for (double r : readings) sum += r;
        return sum / readings.size();
    }

    double[] getAllReadings() {
        double[] copy = new double[readings.size()];
        for (int i = 0; i < readings.size(); i++) copy[i] = readings.get(i);
        return copy;
    }

    public static void main(String[] args) {
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println(java.util.Arrays.toString(v.getAllReadings()));

        double[] copy = v.getAllReadings();
        copy[0] = 999;
        System.out.println(v.getAllReadings()[0]);
    }
}