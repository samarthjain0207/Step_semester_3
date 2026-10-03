import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

enum LeaveStatus {
    PENDING, APPROVED, REJECTED;

    boolean canMoveTo(LeaveStatus next) {
        return this == PENDING && (next == APPROVED || next == REJECTED);
    }
    String label() { return name().charAt(0) + name().substring(1).toLowerCase(); }
}

abstract class Employee {
    private final String name;
    Employee(String name) { this.name = name; }
    String getName() { return name; }
    abstract String getType();
    abstract int getMaxDaysPerRequest();
}

class FullTimeEmployee extends Employee {
    FullTimeEmployee(String n) { super(n); }
    String getType() { return "Full-time"; }
    int getMaxDaysPerRequest() { return 15; }
}

class PartTimeEmployee extends Employee {
    PartTimeEmployee(String n) { super(n); }
    String getType() { return "Part-time"; }
    int getMaxDaysPerRequest() { return 7; }
}

class ContractEmployee extends Employee {
    ContractEmployee(String n) { super(n); }
    String getType() { return "Contract"; }
    int getMaxDaysPerRequest() { return 3; }
}

class LeaveRequest {
    private final Employee employee;
    private final LocalDate start, end;
    private LeaveStatus status = LeaveStatus.PENDING;

    LeaveRequest(Employee e, LocalDate start, LocalDate end) {
        this.employee = e; this.start = start; this.end = end;
    }
    Employee getEmployee() { return employee; }
    LocalDate getStart() { return start; }
    LocalDate getEnd() { return end; }
    LeaveStatus getStatus() { return status; }
    long days() { return ChronoUnit.DAYS.between(start, end) + 1; }

    void changeStatus(LeaveStatus next) {
        if (!status.canMoveTo(next)) {
            if (next == LeaveStatus.PENDING)
                throw new IllegalStateException("Cannot change status: " + status.label() + " request cannot revert to Pending.");
            throw new IllegalStateException("Cannot change status from " + status.label() + " to " + next.label() + ".");
        }
        status = next;
    }
}

class LeaveManager {
    LeaveRequest submit(Employee e, LocalDate start, LocalDate end) {
        if (end.isBefore(start)) throw new IllegalArgumentException("End date before start date.");
        LeaveRequest req = new LeaveRequest(e, start, end);
        if (req.days() > e.getMaxDaysPerRequest())
            throw new IllegalArgumentException(e.getType() + " employees can request at most "
                    + e.getMaxDaysPerRequest() + " days.");
        System.out.println("Leave request submitted by " + e.getName() + " for " + start + " to " + end + ". Status: Pending.");
        return req;
    }

    void review(LeaveRequest req, boolean approve) {
        req.changeStatus(approve ? LeaveStatus.APPROVED : LeaveStatus.REJECTED);
        System.out.println("Leave request for " + req.getEmployee().getName() + " "
                + (approve ? "approved" : "rejected") + ". Status: " + req.getStatus().label() + ".");
    }
}

public class EmployeeLeaveManagement {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();
        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest r1 = manager.submit(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.review(r1, true);
        manager.submit(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        try { r1.changeStatus(LeaveStatus.PENDING); }
        catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }
}
