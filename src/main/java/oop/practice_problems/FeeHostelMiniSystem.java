class FeeHostelMiniSystem {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    FeeHostelMiniSystem(String name, String regNo, HostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;
        totalStudents++;
    }

    String fullStatus() {
        String roomInfo = (room == null) ? "unallotted" : room.roomNo;
        return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomInfo;
    }

    public static void main(String[] args) {
        FeeHostelMiniSystem ravi = new FeeHostelMiniSystem("Ravi", "R1", new HostelFeeAccount("R1", 200000, 60000));
        FeeHostelMiniSystem anitha = new FeeHostelMiniSystem("Anitha", "R2", new HostelFeeAccount("R2", 200000, 20000));
        FeeHostelMiniSystem karthik = new FeeHostelMiniSystem("Karthik", "R3", new HostelFeeAccount("R3", 200000, 0));

        HostelRoom c214 = new HostelRoom("C-214", 2, 1);
        HostelRoom c507 = new HostelRoom("C-507", 2, 1);
        HostelRoom[] rooms = { c214, c507 };

        HostelRoom.safeAllot(rooms, ravi.name);
        ravi.room = c214;
        HostelRoom.safeAllot(rooms, anitha.name);
        anitha.room = c507;
        // Karthik left unallotted on purpose

        ravi.feeAccount.pay(-500); // rejected payment

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());
        System.out.println("Total students: " + FeeHostelMiniSystem.totalStudents);
    }
}