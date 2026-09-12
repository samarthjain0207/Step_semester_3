class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "private":
                return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
            case "default":
                return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";
            case "protected":
                return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";
            case "public":
                return "ALLOWED";
            default:
                throw new IllegalArgumentException("Unknown modifier: " + fieldModifier);
        }
    }

    static String summarizeByModifier(String[][] attempts) {
        String[] order = {"private", "default", "protected", "public"};
        java.util.Map<String, int[]> counts = new java.util.LinkedHashMap<>();
        for (String m : order) counts.put(m, new int[2]); // [allowed, denied]

        for (String[] a : attempts) {
            String result = classifyAccess(a[0], a[1]);
            int[] c = counts.get(a[0]);
            if (result.equals("ALLOWED")) c[0]++; else c[1]++;
        }

        StringBuilder sb = new StringBuilder();
        for (String m : order) {
            int[] c = counts.get(m);
            if (c[0] == 0 && c[1] == 0) continue; // skip modifiers with no attempts
            if (sb.length() > 0) sb.append(" | ");
            sb.append(m).append(": ").append(c[0]).append(" allowed / ").append(c[1]).append(" denied");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE"));
        System.out.println(summarizeByModifier(new String[][]{
                {"private", "SAME_CLASS"}, {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"}, {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"}, {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        }));
    }
}

class LibraryMember {
    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("membershipId must be at least 4 non-blank characters");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}