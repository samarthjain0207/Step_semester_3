package oop.assignment_problems;

public class PremiumDeliveryAccount extends DeliveryAccount {

    private static final double PREMIUM_DISCOUNT = 0.20;

    public PremiumDeliveryAccount(
            String studentId,
            double orderValue) {

        super(studentId, orderValue);
    }

    public PremiumDeliveryAccount(String studentId) {
        super(studentId);
    }

    public double calculatePremiumSurgeFee(int delayMinutes) {

        double normalFee = calculateSurgeFee(delayMinutes);

        return normalFee * (1 - PREMIUM_DISCOUNT);
    }
}