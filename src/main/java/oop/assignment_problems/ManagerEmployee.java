public class ManagerEmployee extends Employee {

    private double teamBonus;

    public ManagerEmployee(
            int empId,
            String empName,
            double salary,
            double teamBonus) {

        super(empId, empName, salary);

        this.teamBonus = teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}