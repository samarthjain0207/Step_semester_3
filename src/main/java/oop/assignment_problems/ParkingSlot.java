public class ParkingSlot {

    private String slotNo;
    private int capacity;
    private int occupiedCount;

    public ParkingSlot(
            String slotNo,
            int capacity,
            int occupiedCount) {

        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public void allot(String vehicleNo) {

        if (occupiedCount < capacity) {

            occupiedCount++;

            System.out.println(
                vehicleNo +
                " allotted to slot " +
                slotNo
            );
        }
    }

    public static ParkingSlot findAvailableSlot(
            ParkingSlot[] slots) {

        for (ParkingSlot slot : slots) {

            if (slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    public static void safeAllot(
            ParkingSlot[] slots,
            String vehicleNo) {

        ParkingSlot slot =
                findAvailableSlot(slots);

        // Check for null before using the object
        if (slot != null) {

            slot.allot(vehicleNo);

        } else {

            System.out.println(
                "No slots available for " +
                vehicleNo
            );
        }
    }

    // Getter needed for F5
    public String getSlotNo() {
        return slotNo;
    }

    public static void main(String[] args) {

        // Test 1: A1 is available
        ParkingSlot[] slots1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };

        safeAllot(slots1, "TN09AB1234");


        // Test 2: All slots are full
        ParkingSlot[] slots2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };

        safeAllot(slots2, "TN09AB1234");
    }
}