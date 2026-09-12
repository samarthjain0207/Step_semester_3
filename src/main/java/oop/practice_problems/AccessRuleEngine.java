class AccessRuleEngine {

    static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "private":
                return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
            case "default":
                return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";
            case "protected":
                // In this basic 3-context version, protected behaves like default.
                return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";
            case "public":
                return "ALLOWED";
            default:
                throw new IllegalArgumentException("Unknown modifier: " + fieldModifier);
        }
    }

    static String summarizeBatch(String[][] attempts) {
        int allowed = 0, denied = 0;
        for (String[] a : attempts) {
            if (classifyAccess(a[0], a[1]).equals("ALLOWED")) allowed++;
            else denied++;
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("default", "DIFFERENT_PACKAGE"));
        System.out.println(summarizeBatch(new String[][]{
                {"protected", "SAME_PACKAGE"},
                {"protected", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        }));
    }
}

class PatientRecord {
    private String patientId;      // sensitive identifier — private
    String wardCode;                // default — visible within hospital package
    protected double vitalsScore;   // protected — subclasses (e.g. ICU module) need it
    public String facilityName;     // public — safe to expose broadly

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().length() < 4) {
            throw new IllegalArgumentException("patientId must be at least 4 non-blank characters");
        }
        this.patientId = patientId.trim();
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}