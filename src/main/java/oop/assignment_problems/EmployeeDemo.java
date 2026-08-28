public class EmployeeDemo {

    public static void main(String[] args) {

        Employee employee =
                new Employee(101, "Amit", 40000);

        ManagerEmployee manager =
                new ManagerEmployee(
                    102,
                    "Divya",
                    70000,
                    8000
                );

        InternEmployee intern =
                new InternEmployee(
                    103,
                    "Meera",
                    12000,
                    10000
                );

        Employee[] employees = {
            employee,
            manager,
            intern
        };

        for (Employee e : employees) {

            if (e instanceof ManagerEmployee) {

                ManagerEmployee m =
                        (ManagerEmployee) e;

                System.out.println(
                    "Manager effective pay: Rs " +
                    m.effectiveSalary()
                );

            } else if (e instanceof InternEmployee) {

                InternEmployee i =
                        (InternEmployee) e;

                System.out.println(
                    "Intern effective pay: Rs " +
                    i.effectiveSalary()
                );

            } else {

                System.out.println(
                    "Plain employee pay: Rs " +
                    e.getSalary()
                );
            }
        }
    }
}