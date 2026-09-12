class BmiCalculator {

    static String getBmiStatus(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }

    static void printWellnessReport(double[] heights, double[] weights) {
        System.out.println("Person | Height (m) | Weight (kg) | BMI   | Status");
        for (int i = 0; i < heights.length; i++) {
            double bmi = weights[i] / (heights[i] * heights[i]);
            double roundedBmi = Math.round(bmi * 100) / 100.0;
            System.out.println("Person " + (i + 1) + " | " + heights[i] + "       | " + weights[i]
                    + "         | " + roundedBmi + " | " + getBmiStatus(bmi));
        }
    }

    public static void main(String[] args) {
        double[] heights = {1.75, 1.60, 1.68, 1.82, 1.55, 1.70, 1.90, 1.65, 1.72, 1.60};
        double[] weights = {70, 90, 55, 85, 48, 65, 100, 60, 75, 52};

        printWellnessReport(heights, weights);
    }
}