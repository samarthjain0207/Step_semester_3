public class CompanyEmployeeRecord {

    String name;
    String empId;

    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    public CompanyEmployeeRecord(
            String name,
            String empId,
            Employee employee,
            ParkingSlot slot) {

        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;

        totalRecords++;
    }

    public String fullProfile() {

        double pay;

        // Manager
        if (employee instanceof ManagerEmployee) {

            ManagerEmployee manager =
                    (ManagerEmployee) employee;

            pay = manager.effectiveSalary();

        // Intern
        } else if (employee instanceof InternEmployee) {

            InternEmployee intern =
                    (InternEmployee) employee;

            pay = intern.effectiveSalary();

        // Normal employee
        } else {

            pay = employee.getSalary();
        }


        String slotInfo;

        if (slot != null) {

            slotInfo = slot.getSlotNo();

        } else {

            slotInfo = "no parking assigned";
        }

        return name +
                " | Pay: Rs " +
                pay +
                " | Slot: " +
                slotInfo;
    }


    public static void main(String[] args) {

        // Manager
        Employee manager =
                new ManagerEmployee(
                    101,
                    "Divya",
                    70000,
                    8000
                );

        // Normal employee
        Employee employee =
                new Employee(
                    102,
                    "Karan",
                    40000
                );

        // Intern
        Employee intern =
                new InternEmployee(
                    103,
                    "Meera",
                    12000,
                    10000
                );


        // Parking slots
        ParkingSlot slot1 =
                new ParkingSlot("A1", 4, 0);

        ParkingSlot slot2 =
                new ParkingSlot("A2", 4, 0);


        // Employee records
        CompanyEmployeeRecord record1 =
                new CompanyEmployeeRecord(
                    "Divya",
                    "101",
                    manager,
                    slot1
                );

        CompanyEmployeeRecord record2 =
                new CompanyEmployeeRecord(
                    "Karan",
                    "102",
                    employee,
                    slot2
                );

        // Meera has no parking slot
        CompanyEmployeeRecord record3 =
                new CompanyEmployeeRecord(
                    "Meera",
                    "103",
                    intern,
                    null
                );


        // Print profiles
        System.out.println(record1.fullProfile());
        System.out.println(record2.fullProfile());
        System.out.println(record3.fullProfile());

        // Total records
        System.out.println(
            "Total records: " +
            CompanyEmployeeRecord.totalRecords
        );
    }
}