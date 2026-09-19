public class RaceUtils {

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Direct subclass";
        }
        return "Base class";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;
        for (RaceEntry e : entries) {
            total += e.getBalanceDue();
        }
        return total;
    }

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder sb = new StringBuilder();
        for (RaceEntry e : entries) {
            sb.append(e.announce());
            if (e instanceof RelayTeamEntry) {
                sb.append(" [Team size via downcast: ").append(((RelayTeamEntry) e).getTeamSize()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0, nullSkipped = 0, relay = 0, individual = 0;
        for (RaceEntry e : entries) {
            if (e == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (e instanceof RelayTeamEntry) relay++;
            else individual++;
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | " + individual + " individual";
    }
}
