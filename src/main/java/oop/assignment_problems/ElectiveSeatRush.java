import java.util.*;

interface CreditPolicy {
    String getType();
    int getLimit();
}

class RegularPolicy  implements CreditPolicy { public String getType() { return "Regular";  } public int getLimit() { return 24; } }
class HonorsPolicy   implements CreditPolicy { public String getType() { return "Honors";   } public int getLimit() { return 28; } }
class ExchangePolicy implements CreditPolicy { public String getType() { return "Exchange"; } public int getLimit() { return 20; } }

class Student {
    private final String name;
    private final CreditPolicy policy;
    private int credits;

    Student(String name, CreditPolicy policy, int currentCredits) {
        this.name = name; this.policy = policy; this.credits = currentCredits;
    }
    String getName() { return name; }
    String getType() { return policy.getType(); }
    int getLimit() { return policy.getLimit(); }
    int getCredits() { return credits; }
    boolean wouldExceed(int extra) { return credits + extra > policy.getLimit(); }
    void addCredits(int c) { credits += c; }
    void removeCredits(int c) { credits -= c; }
}

class Enrollment {
    final Student student; final Elective elective;
    Enrollment(Student s, Elective e) { student = s; elective = e; }
}

class Elective {
    private final String name;
    private final int credits, capacity;
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final Deque<Student> waitlist = new ArrayDeque<>();   // private FIFO queue

    Elective(String name, int credits, int capacity) {
        this.name = name; this.credits = credits; this.capacity = capacity;
    }
    String getName() { return name; }
    int getCredits() { return credits; }
    boolean isFull() { return enrollments.size() >= capacity; }

    boolean isEnrolled(Student s) {
        for (Enrollment e : enrollments) if (e.student == s) return true;
        return false;
    }
    boolean isWaitlisted(Student s) { return waitlist.contains(s); }

    // Invariant: enrollments.size() <= capacity. All mutations are synchronized.
    synchronized void enroll(Student s) {
        if (isFull()) throw new IllegalStateException("No seats available in " + name);
        enrollments.add(new Enrollment(s, this));
        s.addCredits(credits);
    }

    synchronized int addToWaitlist(Student s) {
        waitlist.addLast(s);
        return waitlist.size();
    }

    synchronized boolean removeFromWaitlist(Student s) { return waitlist.remove(s); }

    // Drop + promote as ONE consistent operation. Returns the promoted student or null.
    synchronized Student dropAndPromote(Student s) {
        Iterator<Enrollment> eit = enrollments.iterator();
        while (eit.hasNext()) {
            if (eit.next().student == s) { eit.remove(); s.removeCredits(credits); break; }
        }
        Iterator<Student> wit = waitlist.iterator();
        while (wit.hasNext()) {
            Student w = wit.next();
            if (!w.wouldExceed(credits)) {       // credit limit re-checked on promotion
                wit.remove();
                enroll(w);
                return w;
            }
        }
        return null;
    }
}

class EnrollmentService {
    void enroll(Student s, Elective e) {
        if (e.isEnrolled(s)) { System.out.println(s.getName() + " is already enrolled in " + e.getName() + "."); return; }
        if (e.isWaitlisted(s)) { System.out.println(s.getName() + " is already on the waitlist for " + e.getName() + "."); return; }

        if (s.wouldExceed(e.getCredits())) {     // credit limit BEFORE seat availability
            System.out.println("Enrollment failed: " + s.getName() + " would exceed the " + s.getType()
                    + " credit limit (" + (s.getCredits() + e.getCredits()) + "/" + s.getLimit() + ").");
            return;
        }
        if (e.isFull()) {
            int pos = e.addToWaitlist(s);
            System.out.println(e.getName() + " is full. " + s.getName() + " added to waitlist (position " + pos + ").");
            return;
        }
        e.enroll(s);
        System.out.println(s.getName() + " enrolled in " + e.getName() + " (credits: " + s.getCredits() + "/" + s.getLimit() + ").");
    }

    void drop(Student s, Elective e) {
        if (e.removeFromWaitlist(s)) {
            System.out.println(s.getName() + " removed from the waitlist of " + e.getName() + ".");
            return;
        }
        if (!e.isEnrolled(s)) {
            System.out.println("Drop failed: " + s.getName() + " is not enrolled in " + e.getName() + ".");
            return;
        }
        Student promoted = e.dropAndPromote(s);
        System.out.println(s.getName() + " dropped " + e.getName() + " (credits: " + s.getCredits() + "/" + s.getLimit() + ").");
        if (promoted != null)
            System.out.println(promoted.getName() + " promoted from waitlist and enrolled in " + e.getName()
                    + " (credits: " + promoted.getCredits() + "/" + promoted.getLimit() + ").");
    }
}

public class ElectiveSeatRush {
    public static void main(String[] args) {
        Elective cloud = new Elective("Cloud Computing", 4, 2);
        Student asha  = new Student("Asha",  new RegularPolicy(), 20);
        Student ravi  = new Student("Ravi",  new HonorsPolicy(), 22);
        Student neha  = new Student("Neha",  new ExchangePolicy(), 12);
        Student kiran = new Student("Kiran", new RegularPolicy(), 22);

        EnrollmentService service = new EnrollmentService();
        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);     // full -> waitlist
        service.enroll(kiran, cloud);    // credit limit
        service.drop(asha, cloud);       // Neha auto-promoted
    }
}
