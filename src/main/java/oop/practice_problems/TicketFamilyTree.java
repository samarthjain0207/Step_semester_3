public class TicketFamilyTree {

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

        public String printTicket() {
            return "Standard Event Ticket | Balance Due: " + getBalanceDue();
        }
    }

    static class WorkshopTicket extends EventTicket {
        protected String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        @Override
        public String printTicket() {
            return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
        }
    }

    static class PremiumWorkshopTicket extends WorkshopTicket {
        private double kitFee;

        public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        @Override
        public String printTicket() {
            return "Premium Workshop Ticket | Track: " + track + " | Kit Fee: " + kitFee
                    + " | Balance Due: " + getBalanceDue();
        }
    }

    static class HackathonTicket extends EventTicket {
        private String teamName;

        public HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        @Override
        public String printTicket() {
            return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
        }
    }

    static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Direct subclass";
        }
        return "Base class";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        for (EventTicket t : tickets) {
            total += t.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standard = new EventTicket("STU1", 500);
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premium = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathon = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(standard.printTicket());
        System.out.println(workshop.printTicket());
        System.out.println(premium.printTicket());
        System.out.println(hackathon.printTicket());

        System.out.println(classifyGeneration(premium)); // Multilevel descendant (3 generations deep)
        System.out.println(classifyGeneration(hackathon)); // Hierarchical sibling (independent branch)

        System.out.println(getTotalBalanceDue(
                new EventTicket[]{standard, workshop, premium, hackathon})); // 4500.0
    }
}
