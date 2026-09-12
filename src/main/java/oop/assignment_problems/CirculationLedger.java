class LoanReceipt {
    private String memberId;
    private String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        for (String id : bookIds) {
            if (!isValidBookId(id)) {
                throw new IllegalArgumentException("Invalid book ID: " + id);
            }
        }
        this.memberId = memberId;
        this.bookIds = bookIds.clone();
    }

    private static boolean isValidBookId(String id) {
        return id != null && id.matches("BK-\\d{3}");
    }

    String getMemberId() {
        return memberId;
    }

    String[] getBookIds() {
        return bookIds.clone();
    }

    LoanReceipt withCorrectedBookId(int index, String newId) {
        String[] updated = bookIds.clone();
        updated[index] = newId;
        return new LoanReceipt(memberId, updated);
    }
}

final class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    String getRoomNumber() {
        return roomNumber;
    }
}

class CirculationLedger {
    static int totalNightsProcessed;
    static { totalNightsProcessed = 0; }

    static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0, nullSkipped = 0, referenceOnly = 0, regular = 0;

        for (LoanReceipt r : receipts) {
            if (r == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (r instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        totalNightsProcessed++;
        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]); // BK-100

        String result = processNightlyCirculation(new LoanReceipt[]{
                new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
                null,
                new LoanReceipt("LIB-002", new String[]{"BK-201"})
        });
        System.out.println(result);
    }
}