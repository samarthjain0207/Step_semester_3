import java.io.*;
import java.util.*;

public class PlacementShortlist {

    static class Candidate {
        final String name;
        final int[] scores;
        final int total;
        int bestGain, bestFrom, bestTo;

        Candidate(String name, int[] scores) {
            this.name = name;
            this.scores = scores;
            int s = 0;
            for (int x : scores) s += x;
            this.total = s;
            computeBestClimb();
        }

        private void computeBestClimb() {
            int k = scores.length;
            int[] d = new int[k - 1];
            for (int i = 0; i < k - 1; i++) d[i] = scores[i + 1] - scores[i];
            int best = Integer.MIN_VALUE, bf = 0, bt = 0;
            for (int i = 0; i < d.length; i++) {
                int sum = 0;
                for (int j = i; j < d.length; j++) {
                    sum += d[j];
                    if (sum > best) {
                        best = sum;
                        bf = i + 1;
                        bt = j + 2;
                    }
                }
            }
            bestGain = best;
            bestFrom = bf;
            bestTo = bt;
        }
    }

    static void mergeSort(Candidate[] a, Candidate[] tmp, int lo, int hi) {
        if (hi - lo < 2) return;
        int mid = (lo + hi) >>> 1;
        mergeSort(a, tmp, lo, mid);
        mergeSort(a, tmp, mid, hi);
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            if (a[i].total >= a[j].total) tmp[k++] = a[i++];
            else tmp[k++] = a[j++];
        }
        while (i < mid) tmp[k++] = a[i++];
        while (j < hi) tmp[k++] = a[j++];
        System.arraycopy(tmp, lo, a, lo, hi - lo);
    }

    static int countAtLeast(Candidate[] a, int x) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid].total >= x) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    static String findPair(Candidate[] a, int target) {
        int i = 0, j = a.length - 1;
        while (i < j) {
            int sum = a[i].total + a[j].total;
            if (sum == target) return a[i].name + " + " + a[j].name;
            if (sum > target) i++;
            else j--;
        }
        return "no pair";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append(' ');
        StringTokenizer tk = new StringTokenizer(all.toString());

        int n = Integer.parseInt(tk.nextToken());
        Candidate[] c = new Candidate[n];
        for (int i = 0; i < n; i++) {
            String name = tk.nextToken();
            int[] sc = new int[6];
            for (int j = 0; j < 6; j++) sc[j] = Integer.parseInt(tk.nextToken());
            c[i] = new Candidate(name, sc);
        }

        mergeSort(c, new Candidate[n], 0, n);

        StringBuilder out = new StringBuilder("Shortlist:\n");
        for (int i = 0; i < n; i++) {
            Candidate x = c[i];
            out.append(i + 1).append(". ").append(x.name).append(' ').append(x.total)
               .append(" | best climb ").append(x.bestGain >= 0 ? "+" : "").append(x.bestGain)
               .append(" (test ").append(x.bestFrom).append(" to ").append(x.bestTo).append(")\n");
        }

        while (tk.hasMoreTokens()) {
            String cmd = tk.nextToken();
            if (!tk.hasMoreTokens()) break;
            int v = Integer.parseInt(tk.nextToken());
            if (cmd.equalsIgnoreCase("CUTOFF")) {
                out.append("CUTOFF ").append(v).append(": ").append(countAtLeast(c, v)).append(" candidates\n");
            } else if (cmd.equalsIgnoreCase("PAIR")) {
                out.append("PAIR ").append(v).append(": ").append(findPair(c, v)).append('\n');
            }
        }
        out.append("Merge sort O(n log n) replaces O(n^2); CUTOFF O(log n) replaces O(n); PAIR O(n) replaces O(n^2)\n");
        System.out.print(out);
    }
}
