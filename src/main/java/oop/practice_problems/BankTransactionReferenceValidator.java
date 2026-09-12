class BankTransactionReferenceValidator {

    static String normalizeReference(String raw) {
        String trimmed = raw.trim();
        if (trimmed.length() < 3) return trimmed; // let validation catch the length issue

        String bankCode = trimmed.substring(0, 3).toUpperCase();
        String rest = trimmed.substring(3);

        return bankCode + rest;
    }

    static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: reference must be exactly 14 characters";
        }

        String bankCode = reference.substring(0, 3);
        for (int i = 0; i < bankCode.length(); i++) {
            if (!Character.isLetter(bankCode.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        String body = reference.substring(3);
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: date and sequence must be digits only";
            }
        }

        String datePart = reference.substring(3, 9);   // ddMMyy
        String seqPart = reference.substring(9, 14);    // sequence

        String dd = datePart.substring(0, 2);
        String mm = datePart.substring(2, 4);
        String yy = datePart.substring(4, 6);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(bankCode).append("] DATE: ")
          .append(dd).append("/").append(mm).append("/").append(yy)
          .append(" | SEQ: ").append(seqPart);

        return sb.toString();
    }

    public static void main(String[] args) {
        String normalized1 = normalizeReference(" hdf03022600042 ");
        System.out.println(validateAndFormat(normalized1));

        String normalized2 = normalizeReference("12F03022600042");
        System.out.println(validateAndFormat(normalized2));
    }
}