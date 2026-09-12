final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("ticketFare/minutesLate cannot be negative");
        }
        if (minutesLate == 0) return 0.0;

        int tier1 = Math.min(minutesLate, 5);
        int tier2 = Math.max(0, Math.min(minutesLate, 15) - 5);
        int tier3 = Math.max(0, minutesLate - 15);

        double percent = tier1 * 0.5 + tier2 * 1.0 + tier3 * 2.0;
        double tiered = ticketFare * percent / 100.0;
        double floor = ticketFare * minimumPenaltyPercent / 100.0;

        return Math.max(tiered, floor);
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1);
        System.out.println(calc.calculatePenalty(1000, 0));
        System.out.println(calc.calculatePenalty(1000, 1));
        System.out.println(calc.calculatePenalty(1000, 16));
    }
}