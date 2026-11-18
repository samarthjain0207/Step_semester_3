import java.io.*;
import java.util.*;

public class RoundRobinScheduler {

    static class Job {
        final String name;
        int remaining;

        Job(String name, int remaining) {
            this.name = name;
            this.remaining = remaining;
        }
    }

    static class LabScheduler {
        private final int quantum;
        private final Queue<Job> queue = new ArrayDeque<>();

        LabScheduler(int quantum) {
            this.quantum = quantum;
        }

        void addJob(String name, int time) {
            queue.add(new Job(name, time));
        }

        void run() {
            int now = 0;
            StringBuilder slices = new StringBuilder();
            StringBuilder finish = new StringBuilder("finish ");
            boolean firstFinish = true;

            while (!queue.isEmpty()) {
                Job j = queue.poll();
                int run = Math.min(quantum, j.remaining);
                if (slices.length() > 0) slices.append(' ');
                slices.append(j.name).append('(').append(now).append('-').append(now + run).append(')');
                now += run;
                j.remaining -= run;
                if (j.remaining > 0) {
                    queue.add(j);
                } else {
                    if (!firstFinish) finish.append(", ");
                    finish.append(j.name).append(' ').append(now);
                    firstFinish = false;
                }
            }
            System.out.println(slices);
            System.out.println(finish);
            System.out.println("Time: O(n + T/q) queue operations (n jobs, total time T, quantum q)");
            System.out.println("Space: O(n)");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        while (line != null && line.trim().isEmpty()) line = br.readLine();
        String[] first = line.trim().split("\\s+");
        int q = Integer.parseInt(first[first.length - 1]);
        LabScheduler s = new LabScheduler(q);
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] t = line.split("\\s+");
            if (t.length < 2) continue;
            s.addJob(t[0], Integer.parseInt(t[1]));
        }
        s.run();
    }
}
