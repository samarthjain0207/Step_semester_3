public class TicketRegistrationValidator {

    static class EventTicket {
        protected String attendeeId;
        protected double basePrice;
        protected double amountPaid;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid attendeeId");
            }
            this.attendeeId = attendeeId;
            this.basePrice = basePrice;
        }

        public void pay(double amount) {
            amountPaid += amount;
        }

        public double getBalanceDue() {
            return basePrice - amountPaid;
        }

        public static String registerBatch(String[] attendeeIds, double basePrice) {
            int registered = 0, rejected = 0;
            for (String id : attendeeIds) {
                try {
                    new EventTicket(id, basePrice);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
            return "Registered: " + registered + " | Rejected: " + rejected;
        }
    }

    static class WorkshopTicket extends EventTicket {
        protected String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }
    }

    public static void main(String[] args) {
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue()); // 700.0

        System.out.println(EventTicket.registerBatch(
                new String[]{"STU1", "ST1", "STU2", " ", "STU3"}, 500)); // Registered: 3 | Rejected: 2
    }
}
