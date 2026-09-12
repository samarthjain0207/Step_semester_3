package oop.assignment_problems;
public class FoodOrder {

    private String studentName;
    private String dishName;
    private boolean delivered;

    public FoodOrder(String studentName, String dishName) {

        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name");
        }

        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name");
        }

        this.studentName = studentName;
        this.dishName = dishName;
        this.delivered = false;
    }

    public void markDelivered() {

        if (!delivered) {
            delivered = true;
            System.out.println("Order delivered: " + dishName);
        } else {
            System.out.println("Warning: Order was already delivered: " + dishName);
        }
    }

    public static void processBatch(String[][] rawOrders) {

        int valid = 0;
        int rejected = 0;

        for (String[] order : rawOrders) {

            try {
                if (order == null || order.length < 2) {
                    throw new IllegalArgumentException("Invalid order");
                }

                FoodOrder foodOrder =
                        new FoodOrder(order[0], order[1]);

                valid++;

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {

        String[][] orders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        processBatch(orders);

        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");

        order.markDelivered();
        order.markDelivered();
    }
}