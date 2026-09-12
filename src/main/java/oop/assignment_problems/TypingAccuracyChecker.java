class TypingAccuracyChecker {

    static void checkTypingAccuracy(String original, String typed) {
        int matched = 0;
        int firstMismatchPos = -1;

        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatchPos == -1) {
                firstMismatchPos = i;
            }
        }

        double accuracy = (matched * 100.0) / original.length();
        String accuracyStr = String.format("%.2f", accuracy);

        if (firstMismatchPos == -1) {
            System.out.println("Matched: " + matched + "/" + original.length()
                    + " | Accuracy: " + accuracyStr + "% | No Mismatches");
        } else {
            System.out.println("Matched: " + matched + "/" + original.length()
                    + " | Accuracy: " + accuracyStr + "% | First Mismatch at position "
                    + (firstMismatchPos + 1) + " ('" + original.charAt(firstMismatchPos)
                    + "' vs '" + typed.charAt(firstMismatchPos) + "')");
        }
    }

    public static void main(String[] args) {
        checkTypingAccuracy("hello world", "hello worlt");
        checkTypingAccuracy("coding", "coding");
    }
}