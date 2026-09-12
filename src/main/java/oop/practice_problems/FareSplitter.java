class FareSplitter {
    private String tripId;
    private double totalFare;
    private int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException("Invalid fare or passenger count");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0, 2);
    }

    double[] fareBreakdown() {
        double[] shares = new double[passengerCount];
        double base = Math.floor((totalFare / passengerCount) * 100) / 100.0;
        double runningTotal = 0;

        for (int i = 0; i < passengerCount - 1; i++) {
            shares[i] = base;
            runningTotal += base;
        }
        // last passenger absorbs the leftover so the sum is exact
        shares[passengerCount - 1] = Math.round((totalFare - runningTotal) * 100) / 100.0;
        return shares;
    }

    boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(new FareSplitter("TRIP001", 100000, 3).fareBreakdown()));
        System.out.println(java.util.Arrays.toString(new FareSplitter("TRIP003").fareBreakdown()));
    }
}