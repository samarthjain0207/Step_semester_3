class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn = false;

    public BusTicket(String passengerName, String destination) {
        if (!isValid(passengerName) || !isValid(destination)) {
            throw new IllegalArgumentException("Invalid passengerName or destination");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
    }

    // Meaningful = not null, not blank/whitespace-only, and (for a name) not containing digits.
    private static boolean isValid(String s) {
        if (s == null) return false;
        String trimmed = s.trim();
        if (trimmed.isEmpty()) return false;
        for (char c : trimmed.toCharArray()) {
            if (Character.isDigit(c)) return false;
        }
        return true;
    }

    void markCheckedIn() {
        if (checkedIn) {
            throw new IllegalStateException(passengerName + " is already checked in");
        }
        checkedIn = true;
    }

    String key() {
        return passengerName.toLowerCase() + "|" + destination.toLowerCase();
    }

    static void processBatch(String[][] rawBookings) {
        int valid = 0, rejected = 0, duplicates = 0;
        java.util.Set<String> seen = new java.util.HashSet<>();

        for (String[] entry : rawBookings) {
            try {
                BusTicket t = new BusTicket(entry[0], entry[1]);
                if (!seen.add(t.key())) {
                    duplicates++;
                } else {
                    valid++;
                }
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {
        String[][] batch = {
            {"Divya", "Chennai"}, {"", "Bangalore"}, {"Ravi123", "Pune"},
            {"Divya", "Chennai"}, {" ", " "}
        };
        processBatch(batch);
    }
}