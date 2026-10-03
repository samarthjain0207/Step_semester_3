import java.util.*;

enum HackathonState { OPEN, JUDGING, PUBLISHED }

class Participant {
    private final String name;
    Participant(String name) { this.name = name; }
    String getName() { return name; }
    @Override public boolean equals(Object o) {
        return o instanceof Participant && ((Participant) o).name.equalsIgnoreCase(name);
    }
    @Override public int hashCode() { return name.toLowerCase().hashCode(); }
}

class Score {
    final double idea, execution, presentation;
    Score(double idea, double execution, double presentation) {
        check("Idea", idea); check("Execution", execution); check("Presentation", presentation);
        this.idea = idea; this.execution = execution; this.presentation = presentation;
    }
    private static void check(String label, double v) {
        if (v < 0 || v > 10) throw new IllegalArgumentException(label + " rating must be between 0 and 10.");
    }
}

// Each track owns its scoring rule; new track = new class
interface Track {
    String getName();
    double finalScore(Score s);
}

class InnovationTrack implements Track {
    public String getName() { return "Innovation"; }
    public double finalScore(Score s) { return 0.5 * s.idea + 0.3 * s.execution + 0.2 * s.presentation; }
}

class OpenTrack implements Track {
    public String getName() { return "Open"; }
    public double finalScore(Score s) { return (s.idea + s.execution + s.presentation) / 3.0; }
}

class Project {
    private final String title;
    private final Team team;
    private Score score;
    Project(String title, Team team) { this.title = title; this.team = team; }
    String getTitle() { return title; }
    Team getTeam() { return team; }
    Score getScore() { return score; }
    void setScore(Score s) { this.score = s; }
}

class Team {
    private final String name;
    private final List<Participant> members;
    private final Track track;
    private Project project;
    Team(String name, List<Participant> members, Track track) {
        this.name = name; this.members = new ArrayList<>(members); this.track = track;
    }
    String getName() { return name; }
    int size() { return members.size(); }
    Track getTrack() { return track; }
    Project getProject() { return project; }
    void setProject(Project p) { this.project = p; }
}

class Judge {
    private final String name;
    Judge(String name) { this.name = name; }
    void score(Hackathon h, String projectTitle, double idea, double execution, double presentation) {
        try {
            h.recordScore(projectTitle, new Score(idea, execution, presentation));
        } catch (IllegalArgumentException e) {
            System.out.println("Score rejected: " + e.getMessage());
        }
    }
}

class Hackathon {
    private final String name;
    private HackathonState state = HackathonState.OPEN;
    private final Map<String, Team> teams = new LinkedHashMap<>();
    private final Map<String, Project> projects = new LinkedHashMap<>();
    private final Set<Participant> registered = new HashSet<>();

    Hackathon(String name) { this.name = name; }

    Team registerTeam(String teamName, Track track, Participant... members) {
        if (state != HackathonState.OPEN) {
            System.out.println("Registration failed: Registration is closed."); return null;
        }
        if (teams.containsKey(teamName)) {
            System.out.println("Registration failed: Team name already taken."); return null;
        }
        if (members.length < 2 || members.length > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members."); return null;
        }
        Set<Participant> unique = new HashSet<>();
        for (Participant s : members) {
            if (registered.contains(s) || !unique.add(s)) {
                System.out.println("Registration failed: " + s.getName() + " is already in a team."); return null;
            }
        }
        Team t = new Team(teamName, Arrays.asList(members), track);
        teams.put(teamName, t);
        registered.addAll(unique);
        System.out.println("Team " + teamName + " registered (" + members.length + " members, "
                + track.getName() + " track).");
        return t;
    }

    Project submitProject(String teamName, String title) {
        Team t = teams.get(teamName);
        if (t == null) { System.out.println("Submission failed: Unknown team."); return null; }
        if (state != HackathonState.OPEN) {
            System.out.println("Submission failed: Submissions are closed."); return null;
        }
        if (t.getProject() != null) {
            System.out.println("Submission failed: " + teamName + " has already submitted a project."); return null;
        }
        Project p = new Project(title, t);
        t.setProject(p);
        projects.put(title, p);
        System.out.println("Project '" + title + "' submitted by " + teamName + ".");
        return p;
    }

    void startJudging() {
        if (state == HackathonState.OPEN) state = HackathonState.JUDGING;
    }

    void recordScore(String title, Score score) {
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Rescore rejected: Results have already been published."); return;
        }
        if (state == HackathonState.OPEN) {
            System.out.println("Scoring failed: Judging has not started."); return;
        }
        Project p = projects.get(title);
        if (p == null) { System.out.println("Scoring failed: Unknown project."); return; }
        p.setScore(score);
        double fin = p.getTeam().getTrack().finalScore(score);   // polymorphic
        System.out.println("Score recorded for '" + title + "'.");
        System.out.println("Final score: " + String.format(Locale.US, "%.2f", fin) + ".");
    }

    void publishResults() {
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Results are already published."); return;
        }
        state = HackathonState.PUBLISHED;
        System.out.println("Results published.");
    }
}

public class CodeSprintJudgingDesk {
    public static void main(String[] args) {
        Hackathon h = new Hackathon("Code Sprint");
        Judge judge = new Judge("Judge 1");

        h.registerTeam("ByteBusters", new InnovationTrack(),
                new Participant("Asha"), new Participant("Ravi"), new Participant("Neha"));
        h.registerTeam("SoloCoder", new OpenTrack(), new Participant("Kiran"));   // fails: 1 member

        h.submitProject("ByteBusters", "SmartAttend");
        h.startJudging();
        judge.score(h, "SmartAttend", 8, 7, 9);
        h.publishResults();
        judge.score(h, "SmartAttend", 10, 7, 9);                                  // rejected
    }
}
