import java.util.LinkedHashMap;
import java.util.Map;

class FirstNonRepeatingChar {

    static char findFirstNonRepeatingChar(String text) {
        Map<Character, Integer> frequency = new LinkedHashMap<>();

        for (char c : text.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        for (char c : text.toCharArray()) {
            if (frequency.get(c) == 1) return c;
        }

        return '\0'; // sentinel: no non-repeating character
    }

    public static void main(String[] args) {
        String[] inputs = {"swiss", "aabbcc"};

        for (String text : inputs) {
            char result = findFirstNonRepeatingChar(text);
            System.out.println("\"" + text + "\"");
            if (result == '\0') {
                System.out.println("No Non-Repeating Character Found");
            } else {
                System.out.println("First Non-Repeating Character: '" + result + "'");
            }
        }
    }
}