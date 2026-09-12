class SrmStudent {
    String name;
    String regNo;
    int attendance;

    SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }

    boolean isEligible() {
        return attendance >= 75;
    }

    // classAverage is static because it operates over a collection of students,
    // not on any single student's state — it belongs to the class, not an instance.
    // isEligible is instance-level because it depends on one particular object's attendance.
    static double classAverage(SrmStudent[] students) {
        int sum = 0;
        for (SrmStudent s : students) sum += s.attendance;
        return (double) sum / students.length;
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
            new SrmStudent("Ravi", "R1", 82),
            new SrmStudent("Anitha", "R2", 68),
            new SrmStudent("Karthik", "R3", 91),
            new SrmStudent("Meera", "R4", 74),
            new SrmStudent("Suresh", "R5", 60)
        };

        for (SrmStudent s : students) {
            System.out.println(s.name + " - " + s.attendance + "% - " + (s.isEligible() ? "Eligible" : "Detained"));
        }

        System.out.println("Class average: " + SrmStudent.classAverage(students) + "%");
    }
}