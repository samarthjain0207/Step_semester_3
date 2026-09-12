class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String name) {
        if (occupied < beds) occupied++;
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        for (HostelRoom r : rooms) {
            if (r.occupied < r.beds) return r;
        }
        return null;
    }

    static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom room = findAvailableRoom(rooms);
        if (room == null) {
            System.out.println("No rooms available for " + studentName);
            return;
        }
        room.allot(studentName);
        System.out.println(studentName + " allotted to room " + room.roomNo);
    }

    // The array holds references to HostelRoom objects, not the objects themselves.
    // Passing it into a method copies the reference values (addresses), so both the
    // caller and the method see and mutate the same underlying room objects.
    public static void main(String[] args) {
        HostelRoom[] rooms1 = { new HostelRoom("C-214", 3, 2), new HostelRoom("C-507", 2, 2) };
        safeAllot(rooms1, "Divya");

        HostelRoom[] rooms2 = { new HostelRoom("C-214", 3, 3), new HostelRoom("C-507", 2, 2) };
        safeAllot(rooms2, "Divya");
    }
}