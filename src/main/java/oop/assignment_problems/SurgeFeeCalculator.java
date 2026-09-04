package oop.assignment_problems;

public final class SurgeFeeCalculator {

    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {

        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException(
                    "Minimum surge percentage cannot be negative"
            );
        }

        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(
            double orderValue,
            int delayMinutes) {

        if (orderValue < 0) {
            throw new IllegalArgumentException(
                    "Order value cannot be negative"
            );
        }

        if (delayMinutes < 0) {
            throw new IllegalArgumentException(
                    "Delay minutes cannot be negative"
            );
        }

        // No delay means no surge fee
        if (delayMinutes == 0) {
            return 0.0;
        }

        double tieredFee = 0.0;

        // First 5 minutes = 0.5%
        int firstTierMinutes = Math.min(delayMinutes, 5);
        tieredFee += firstTierMinutes
                * orderValue
                * 0.005;

        // Minutes 6-15 = 1%
        if (delayMinutes > 5) {

            int secondTierMinutes =
                    Math.min(delayMinutes - 5, 10);

            tieredFee += secondTierMinutes
                    * orderValue
                    * 0.01;
        }

        // Minute 16 onwards = 2%
        if (delayMinutes > 15) {

            int thirdTierMinutes =
                    delayMinutes - 15;

            tieredFee += thirdTierMinutes
                    * orderValue
                    * 0.02;
        }

        // Minimum surge floor applies only when delayed
        double minimumFee =
                orderValue * (minimumSurgePercent / 100.0);

        return Math.max(tieredFee, minimumFee);
    }

    public static void main(String[] args) {

        SurgeFeeCalculator calculator =
                new SurgeFeeCalculator(1.0);

        System.out.println(
                "Rs " + calculator.calculateSurgeFee(500, 0)
        );

        System.out.println(
                "Rs " + calculator.calculateSurgeFee(500, 1)
        );

        System.out.println(
                "Rs " + calculator.calculateSurgeFee(500, 16)
        );
    }
}