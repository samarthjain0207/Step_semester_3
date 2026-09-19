import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // ---- Problem 1 ----
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        RunnerEntry p1runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        p1runner.pay(30);
        System.out.println(p1runner.getBalanceDue()); // 50.0
        System.out.println(RaceEntry.registerBatch(new String[]{"BIB1", "B1", "BIB2"}, 80)); // Registered: 2 | Rejected: 1

        // ---- Problem 2 ----
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        System.out.println(runnerEntry.announce());
        System.out.println(eliteEntry.announce());
        System.out.println(relayEntry.announce());
        System.out.println(RaceUtils.classifyGeneration(eliteEntry)); // Multilevel descendant (3 generations deep)
        System.out.println(RaceUtils.classifyGeneration(relayEntry)); // Hierarchical sibling (independent branch)
        System.out.println(RaceUtils.getTotalBalanceDue(new RaceEntry[]{runnerEntry, eliteEntry, relayEntry})); // 530.0

        // ---- Problem 3 (continues with the same runnerEntry) ----
        runnerEntry.pay(30);
        runnerEntry.applyLateFee(20);
        System.out.println(runnerEntry.getBalanceDue()); // 90.0
        double[] history = runnerEntry.getLateFeeHistory();
        System.out.println(Arrays.toString(history)); // [40.0]
        history[0] = 999; // tampering with the returned copy
        System.out.println(Arrays.toString(runnerEntry.getLateFeeHistory())); // still [40.0]

        // ---- Problem 4 (continues with the same runnerEntry and relayEntry) ----
        System.out.println(RaceUtils.announceAll(new RaceEntry[]{runnerEntry, relayEntry}));
        RaceEntry plain = new RaceEntry("BIB5001", 50);
        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain; // throws at runtime
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }

        // ---- Problem 5 ----
        System.out.println(RaceEntry.isValidDiscountCode("M123A")); // true
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));  // false
        System.out.println(RaceEntry.isValidDiscountCode("X123A")); // false
        relayEntry.pay(10, "UPI"); // Paying via UPI
        System.out.println(RaceUtils.settleNight(new RaceEntry[]{eliteEntry, null, relayEntry})); // 2 processed | 1 null skipped | 1 relay | 1 individual
        System.out.println(RaceEntry.getBibCounter()); // total successful constructions so far in this run
    }
}
