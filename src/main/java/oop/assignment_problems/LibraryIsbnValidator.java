class LibraryIsbnValidator {

    static String normalizeCode(String raw) {
        String trimmed = raw.trim();
        if (trimmed.length() < 3) return trimmed; // let validation catch the length issue

        String publisherCode = trimmed.substring(0, 3).toUpperCase();
        String rest = trimmed.substring(3);

        return publisherCode + rest;
    }

    static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: code must be exactly 13 characters";
        }

        String publisherCode = code.substring(0, 3);
        for (int i = 0; i < publisherCode.length(); i++) {
            if (!Character.isLetter(publisherCode.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        String body = code.substring(3);
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: year and catalog number must be digits only";
            }
        }

        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(publisherCode).append("] YEAR: ")
          .append(year).append(" | CATALOG: ").append(catalog);

        return sb.toString();
    }

    public static void main(String[] args) {
        String normalized1 = normalizeCode(" pen2026004251 ");
        System.out.println(validateAndFormat(normalized1));

        String normalized2 = normalizeCode("12N2026004251");
        System.out.println(validateAndFormat(normalized2));
    }
}