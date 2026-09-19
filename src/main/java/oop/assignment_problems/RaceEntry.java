public class RaceEntry {
    private static int entryCounter = 0;
    protected final String entryCode;
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;
    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount = 0;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber");
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        entryCounter++;
        this.entryCode = "ENTRY-" + entryCounter;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String announce() {
        return "Standard Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }

    protected void applyLateFee(double amount) {
        entryFee += amount;
        lateFeeHistory[lateFeeCount++] = amount;
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[lateFeeCount];
        System.arraycopy(lateFeeHistory, 0, copy, 0, lateFeeCount);
        return copy;
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0, rejected = 0;
        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) return false;
        if (code.charAt(0) != 'M') return false;
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) return false;
        }
        return Character.isUpperCase(code.charAt(4));
    }

    public static int getBibCounter() {
        return entryCounter;
    }
}
