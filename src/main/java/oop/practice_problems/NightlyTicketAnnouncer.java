public class NightlyTicketAnnouncer {

    static class EventTicket {
        protected double basePrice;
        protected double amountPaid;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
        }

        public void pay(double amount) {
            amountPaid += amount;
        }

        public double getBalanceDue() {
            return basePrice - amountPaid;
        }

        public String printTicket() {
            return "Standard | Balance: " + getBalanceDue();
        }
    }

    static class WorkshopTicket extends EventTicket {
        private String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        public String getTrack() {
            return track;
        }

        @Override
        public String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
        }
    }

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();
        for (EventTicket t : tickets) {
            sb.append(t.printTicket());
            if (t instanceof WorkshopTicket) {
                sb.append(" [Track via downcast: ").append(((WorkshopTicket) t).getTrack()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String report = batchPrint(new EventTicket[]{
                new EventTicket(500),
                new WorkshopTicket(1200, "AI/ML")
        });
        System.out.println(report);
        // Standard | Balance: 500.0 | Workshop | Track: AI/ML | Balance: 1200.0 [Track via downcast: AI/ML] |

        EventTicket plain = new EventTicket(500);
        try {
            WorkshopTicket bad = (WorkshopTicket) plain; // throws at runtime
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
