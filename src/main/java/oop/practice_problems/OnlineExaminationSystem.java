import java.util.*;

enum AttemptStatus { IN_PROGRESS, SUBMITTED }

abstract class Question {
    protected final int id;
    protected final String text, correctAnswer;
    Question(int id, String text, String correctAnswer) {
        this.id = id; this.text = text; this.correctAnswer = correctAnswer;
    }
    int getId() { return id; }
    abstract boolean isCorrect(String answer);
}

class MultipleChoiceQuestion extends Question {
    MultipleChoiceQuestion(int id, String text, String correct) { super(id, text, correct); }
    boolean isCorrect(String a) { return correctAnswer.equalsIgnoreCase(a.trim()); }
}

class TrueFalseQuestion extends Question {
    TrueFalseQuestion(int id, String text, String correct) { super(id, text, correct); }
    boolean isCorrect(String a) { return correctAnswer.equalsIgnoreCase(a.trim()); }
}

class Examination {
    private final String title;
    private final Map<Integer, Question> questions = new LinkedHashMap<>();
    Examination(String title) { this.title = title; }
    String getTitle() { return title; }
    void addQuestion(Question q) { questions.put(q.getId(), q); }
    Question getQuestion(int id) {
        Question q = questions.get(id);
        if (q == null) throw new IllegalArgumentException("Question " + id + " not found");
        return q;
    }
    int total() { return questions.size(); }
    int evaluate(Map<Integer, String> answers) {
        int score = 0;
        for (Question q : questions.values()) {
            String a = answers.get(q.getId());
            if (a != null && q.isCorrect(a)) score++;   // polymorphic
        }
        return score;
    }
}

class Attempt {
    private final Examinee student;
    private final Examination exam;
    private final Map<Integer, String> answers = new HashMap<>();
    private AttemptStatus status = AttemptStatus.IN_PROGRESS;
    private int score;

    Attempt(Examinee s, Examination e) { student = s; exam = e; }
    Examination getExam() { return exam; }
    AttemptStatus getStatus() { return status; }

    void answer(int qid, String value) {
        if (status == AttemptStatus.SUBMITTED)
            throw new IllegalStateException("Attempt already submitted; answers cannot be changed.");
        exam.getQuestion(qid);
        answers.put(qid, value);
        System.out.println("Question " + qid + " answered with '" + value + "'.");
    }

    void submit() {
        if (status == AttemptStatus.SUBMITTED)
            throw new IllegalStateException("Attempt already submitted.");
        score = exam.evaluate(answers);
        status = AttemptStatus.SUBMITTED;
        System.out.println("Examination '" + exam.getTitle() + "' submitted successfully.");
        System.out.println("Result for '" + exam.getTitle() + "' attempt: " + score + "/" + exam.total() + " correct");
    }
}

class Examinee {
    private final String name;
    private final List<Attempt> attempts = new ArrayList<>();
    Examinee(String name) { this.name = name; }

    boolean hasSubmitted(Examination e) {
        return attempts.stream().anyMatch(a -> a.getExam() == e && a.getStatus() == AttemptStatus.SUBMITTED);
    }

    Attempt startExam(Examination e) {
        if (hasSubmitted(e))
            throw new IllegalStateException("Already submitted an attempt for '" + e.getTitle() + "'.");
        Attempt a = new Attempt(this, e);
        attempts.add(a);
        System.out.println("Examination '" + e.getTitle() + "' started by " + name + ".");
        return a;
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Examination quiz = new Examination("Math Quiz");
        quiz.addQuestion(new MultipleChoiceQuestion(1, "2+2?", "A"));
        quiz.addQuestion(new MultipleChoiceQuestion(2, "3*3?", "B"));

        Examinee s = new Examinee("Student");
        Attempt att = s.startExam(quiz);
        att.answer(1, "A");
        att.answer(2, "C");
        att.submit();

        try { att.answer(1, "B"); } catch (IllegalStateException e) { System.out.println("Blocked: " + e.getMessage()); }
        try { s.startExam(quiz); } catch (IllegalStateException e) { System.out.println("Blocked: " + e.getMessage()); }
    }
}
