// ---- Broken version ----
class BrokenStudent {
    static String name;
    static String regNo;
    static int attendance;

    BrokenStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }
    // name/regNo/attendance being static means there is only ONE copy shared by
    // every BrokenStudent object. Each new student overwrites the previous
    // student's data instead of getting its own — they should be instance fields
    // since each student needs independent state.
}

// ---- Fixed version ----
class StudentStaticVsInstanceDemo {
    String name;
    String regNo;
    int attendance;

    static String university = "SRM";
    static int admissionCount = 0;

    StudentStaticVsInstanceDemo(String name, int attendance) {
        this.name = name;
        admissionCount++;
        this.regNo = "RA23110030" + (1000 + admissionCount);
        this.attendance = attendance;
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }

    public static void main(String[] args) {
        System.out.println("Broken version:");
        BrokenStudent b1 = new BrokenStudent("Ravi", "R1", 80);
        BrokenStudent b2 = new BrokenStudent("Meera", "R2", 90);
        System.out.println(b1.name);
        System.out.println(b2.name);

        System.out.println("\nFixed version:");
        StudentStaticVsInstanceDemo s1 = new StudentStaticVsInstanceDemo("Ravi", 80);
        StudentStaticVsInstanceDemo s2 = new StudentStaticVsInstanceDemo("Meera", 90);
        s1.printIdCard();
        s2.printIdCard();
        printTotalAdmissions();
    }
}