package oop.assignment_problems;
public class DeliveryAccount {

    protected String studentId;
    protected double orderValue;

    protected static SurgeFeeCalculator surgeCalculator;

    static {
        surgeCalculator = new SurgeFeeCalculator(1.0);
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {

        return surgeCalculator.calculateSurgeFee(
                orderValue,
                delayMinutes
        );
    }

    public void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        if (account == null) {
            return;
        }

        double surgeFee = account.calculateSurgeFee(delayMinutes);

        System.out.println(
                "Student: " + account.studentId
        );

        System.out.println(
                "Amount: Rs " + amount
        );

        System.out.println(
                "Surge Fee: Rs " + surgeFee
        );
    }
}