public class Employee {

    private int empId;
    private String empName;
    private double salary;

    public Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}