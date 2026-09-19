import java.util.Arrays;

public class LateFeeAuditTrail{

    static class EventTicket {
        protected double basePrice;
        protected double amountPaid;
        private double[] lateFeeHistory = new double[10];
        private int lateFeeCount = 0;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
        }

        public void pay(double amount) {
            amountPaid += amount;
        }

        public double getBalanceDue() {
            return basePrice - amountPaid;
        }

        protected void applyLateFee(double amount) {
            basePrice += amount;
            lateFeeHistory[lateFeeCount++] = amount;
        }

        public double[] getLateFeeHistory() {
            double[] copy = new double[lateFeeCount];
            System.arraycopy(lateFeeHistory, 0, copy, 0, lateFeeCount);
            return copy;
        }
    }

    static class WorkshopTicket extends EventTicket {
        public WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue()); // 200.0

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history)); // [200.0]
        history[0] = 999; // tampering with the returned copy
        System.out.println(Arrays.toString(w.getLateFeeHistory())); // still [200.0]
    }
}
