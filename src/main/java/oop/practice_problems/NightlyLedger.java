class DischargeSummary {   // <-- removed "final"
    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        for (String code : medicationCodes) {
            if (!isValidCode(code)) {
                throw new IllegalArgumentException("Invalid medication code: " + code);
            }
        }
        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    private static boolean isValidCode(String code) {
        return code != null && code.matches("MED-[A-Z]");
    }

    String getPatientId() {
        return patientId;
    }

    String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    DischargeSummary withCorrectedMedication(int index, String newCode) {
        String[] updated = medicationCodes.clone();
        updated[index] = newCode;
        return new DischargeSummary(patientId, updated);
    }
}

final class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    int getIcuDays() {
        return icuDays;
    }
}

class NightlyLedger {
    static int totalRunsProcessed;
    static { totalRunsProcessed = 0; }

    static String processNightlyBatch(DischargeSummary[] summaries) {
        int processed = 0, nullSkipped = 0, criticalCare = 0, routine = 0;

        for (DischargeSummary s : summaries) {
            if (s == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (s instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
        }

        totalRunsProcessed++;
        return processed + " processed | " + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(d.getMedicationCodes()[0]);

        String result = processNightlyBatch(new DischargeSummary[]{
                new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
                null,
                new DischargeSummary("MT002", new String[]{"MED-Y"})
        });
        System.out.println(result);
    }
}