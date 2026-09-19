public class RunnerEntry extends RaceEntry {
    protected String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + getBalanceDue();
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}
