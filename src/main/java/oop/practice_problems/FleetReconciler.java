class BusTicketAccount {
    static int accountCounter;
    static { accountCounter = 0; }

    protected String bookingId;
    protected double ticketFare;
    private static final BoardingPenaltyCalculator PENALTY_CALC = new BoardingPenaltyCalculator(1);

    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
        accountCounter++;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0);
    }

    final double calculatePenalty(int minutesLate) {
        return PENALTY_CALC.calculatePenalty(ticketFare, minutesLate);
    }
}

class SleeperBusTicketAccount extends BusTicketAccount {
    public SleeperBusTicketAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }
}

class FleetReconciler {
    static void processAccount(BusTicketAccount account, double amount, int minutesLate,
                                 int[] counters, double[] totalPenalty) {
        if (account == null) {
            counters[0]++; // null skipped
            return;
        }
        double penalty = account.calculatePenalty(minutesLate);
        totalPenalty[0] += penalty;
        counters[1]++; // processed

        if (account instanceof SleeperBusTicketAccount) {
            counters[2]++; // sleeper
        } else {
            counters[3]++; // regular
        }
    }

    static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        int len = Math.min(accounts.length, Math.min(amounts.length, minutesLateArray.length));
        if (len != accounts.length || len != amounts.length || len != minutesLateArray.length) {
            System.out.println("Warning: mismatched array lengths — processing only " + len + " entries");
        }

        int[] counters = new int[4]; // 0=nullSkipped 1=processed 2=sleeper 3=regular
        double[] totalPenalty = {0.0};

        for (int i = 0; i < len; i++) {
            processAccount(accounts[i], amounts[i], minutesLateArray[i], counters, totalPenalty);
        }

        System.out.println(counters[1] + " processed | " + counters[0] + " null skipped | "
                + counters[2] + " sleeper | " + counters[3] + " regular | grand total penalties = "
                + totalPenalty[0]);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new SleeperBusTicketAccount("BK001", 2000), null, new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLate = {10, 5, 0};
        processBatch(accounts, amounts, minutesLate);
    }
}