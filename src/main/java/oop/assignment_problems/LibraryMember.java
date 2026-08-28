public class LibraryMember {

    // Instance fields
    private String name;
    private String memberId;
    private int booksIssued;

    // Static fields
    private static String libraryName = "SRM Library";
    private static int memberCount = 0;

    // Constructor
    public LibraryMember(String name, int booksIssued) {

        this.name = name;
        this.booksIssued = booksIssued;

        memberCount++;

        this.memberId =
                "LM-" + (1000 + memberCount);
    }

    // Instance method
    public void printMemberCard() {

        System.out.println(
            name + " | " + memberId
        );
    }

    // Static method
    public static void printTotalMembers() {

        System.out.println(
            "Total members: " +
            memberCount
        );
    }

    public static void main(String[] args) {

        System.out.println("Fixed version:");

        LibraryMember member1 =
                new LibraryMember("Aditi", 2);

        LibraryMember member2 =
                new LibraryMember("Rohan", 3);

        member1.printMemberCard();
        member2.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}