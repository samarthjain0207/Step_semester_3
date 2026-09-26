interface Exportable {
    String exportData();
}

class ExportTracker {
    private static int totalExports = 0;

    static void increment() {
        totalExports++;
    }

    static int getTotal() {
        return totalExports;
    }
}

class ReportGenerator implements Exportable {
    private String reportName;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String exportData() {
        ExportTracker.increment();
        return "Exported report: " + reportName;
    }
}

class UserProfile implements Exportable {
    private String username;

    public UserProfile(String username) {
        this.username = username;
    }

    @Override
    public String exportData() {
        ExportTracker.increment();
        return "Exported profile: " + username;
    }
}

public class OneClickDataExport {
    static int getTotalExports() {
        return ExportTracker.getTotal();
    }

    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        System.out.println(r.exportData());

        UserProfile u = new UserProfile("jane_doe");
        System.out.println(u.exportData());

        Exportable ref = r; // upcasting: ReportGenerator stored as the Exportable interface type
        exportAll(new Exportable[]{ref, u});
        System.out.println(getTotalExports());
    }
}