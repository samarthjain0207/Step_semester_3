import java.util.*;

public class StudentMarksGrid {
    static final int MISSING = -1;

    public static void main(String[] args) {
        String[] subjects = {"Math", "Sci", "Eng"};
        String[] students = {"Anu", "Ravi", "Meena"};
        int[][] marks = {
            {80, 90, 70},
            {60, 85, MISSING},
            {95, 75, 88}
        };

        StringBuilder totals = new StringBuilder("Totals ");
        for (int i = 0; i < students.length; i++) {
            int t = 0;
            for (int m : marks[i]) if (m != MISSING) t += m;
            totals.append(students[i]).append(' ').append(t).append(i < students.length - 1 ? ", " : "");
        }
        System.out.println(totals);

        StringBuilder toppers = new StringBuilder("toppers ");
        for (int j = 0; j < subjects.length; j++) {
            int best = MISSING, who = -1;
            for (int i = 0; i < students.length; i++)
                if (marks[i][j] > best) { best = marks[i][j]; who = i; }
            toppers.append(subjects[j]).append(' ').append(students[who]).append(' ').append(best)
                   .append(j < subjects.length - 1 ? ", " : "");
        }
        System.out.println(toppers);
    }
}
