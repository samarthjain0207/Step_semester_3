public class InternEmployee extends Employee {

    private double stipendCap;

    public InternEmployee(
            int empId,
            String empName,
            double salary,
            double stipendCap) {

        super(empId, empName, salary);

        this.stipendCap = stipendCap;
    }

    public double effectiveSalary() {

        if (getSalary() < stipendCap) {
            return getSalary();
        }

        return stipendCap;
    }
}