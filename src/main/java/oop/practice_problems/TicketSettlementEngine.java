public class TicketSettlementEngine {

    static class EventTicket {
        private static int counter = 1000;
        protected final String ticketId;
        protected double basePrice;
        protected double amountPaid;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
            counter++;
            this.ticketId = "TCK-" + counter;
        }

        public void pay(double amount) {
            amountPaid += amount;
        }

        public void pay(double amount, String mode) {
            System.out.println("Payment mode: " + mode);
            pay(amount);
        }

        public double getBalanceDue() {
            return basePrice - amountPaid;
        }

        public static int getTicketsIssued() {
            return counter - 1000;
        }

        public static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != 5) return false;
            if (code.charAt(0) != 'F') return false;
            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) return false;
            }
            return Character.isUpperCase(code.charAt(4));
        }
    }

    static class GroupTicket extends EventTicket {
        private int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            this.groupSize = groupSize;
        }
    }

    static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0, nullSkipped = 0, group = 0, individual = 0;
        for (EventTicket t : tickets) {
            if (t == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (t instanceof GroupTicket) group++;
            else individual++;
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println(t1.ticketId); // TCK-1001
        System.out.println(EventTicket.getTicketsIssued()); // 1

        System.out.println(EventTicket.isValidPromoCode("F123A")); // true
        System.out.println(EventTicket.isValidPromoCode("F12A"));  // false
        System.out.println(EventTicket.isValidPromoCode("X123A")); // false

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue()); // 100.0

        System.out.println(processNightlySettlement(new EventTicket[]{
                new GroupTicket(2000, 5), null, new EventTicket(500)
        })); // 2 processed | 1 null skipped | 1 group | 1 individual
    }
}
