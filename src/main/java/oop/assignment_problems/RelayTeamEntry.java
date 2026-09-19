public class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
    }
}
