import java.util.ArrayDeque;
import java.util.Deque;

public class HackathonBannerPrinter {
    static String expandBanner(String code) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> texts = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        int k = 0;
        for (int i = 0; i < code.length(); i++) {
            char ch = code.charAt(i);
            if (Character.isDigit(ch)) {
                k = k * 10 + (ch - '0');
            } else if (ch == '[') {
                counts.push(k);
                texts.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                int times = counts.pop();
                StringBuilder prev = texts.pop();
                for (int t = 0; t < times; t++) prev.append(current);
                current = prev;
            } else {
                current.append(ch);
            }
        }
        return current.toString();
    }

    public static void main(String[] args) {
        System.out.println("\"" + expandBanner("3[ab]2[c]") + "\"");
        System.out.println("\"" + expandBanner("2[x3[y]]z") + "\"");
    }
}
