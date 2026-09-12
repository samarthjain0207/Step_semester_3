public class BookIssue {

    private String title;
    private String borrowerName;
    private int daysOverdue;

    // Constructor
    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    // Calculates fine for one book
    public double fineAmount() {
        if (daysOverdue > 0) {
            return daysOverdue * 5;
        }

        return 0;
    }

    // Checks whether the book is overdue by more than 14 days
    public boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    /*
     * static because this method calculates the total
     * for multiple BookIssue objects, not one particular book.
     * fineAmount() is not static because it belongs to one BookIssue object.
     */
    public static double totalFineCollected(BookIssue[] issues) {

        double total = 0;

        for (BookIssue issue : issues) {
            total += issue.fineAmount();
        }

        return total;
    }

    public static void main(String[] args) {

        BookIssue[] issues = {
            new BookIssue("Clean Code", "Aditi", 18),
            new BookIssue("Effective Java", "Rohan", 5),
            new BookIssue("Refactoring", "Karan", 0),
            new BookIssue("DSA Handbook", "Meera", 21),
            new BookIssue("Design Patterns", "Arjun", 9)
        };

        for (BookIssue issue : issues) {

            if (issue.isSeverelyOverdue()) {
                System.out.println(
                    issue.title + " - " +
                    issue.daysOverdue +
                    " days - Severely overdue"
                );
            } else {
                System.out.println(
                    issue.title + " - " +
                    issue.daysOverdue +
                    " days - OK"
                );
            }
        }

        System.out.println(
            "Total fine collected: Rs " +
            BookIssue.totalFineCollected(issues)
        );
    }
}