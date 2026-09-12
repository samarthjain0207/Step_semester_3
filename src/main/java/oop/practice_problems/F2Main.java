class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount");
            return;
        }
        amountPaid += amount;
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {
    HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    ScholarshipFeeAccount(String regNo, double totalFee, double amountPaid, double scholarshipPercent) {
        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    double effectiveDue() {
        return getDue() * (1 - scholarshipPercent / 100.0);
    }
}

public class F2Main {
    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("P1", 150000, 150000);
        HostelFeeAccount hostel = new HostelFeeAccount("H1", 200000, 60000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("S1", 180000, 0, 20);

        hostel.payInTwoInstallments(0); // already paid in constructor for sample

        FeeAccount[] accounts = { plain, hostel, scholarship };
        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                System.out.println("Scholarship account effective due: Rs " + ((ScholarshipFeeAccount) acc).effectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + acc.getDue());
            } else {
                System.out.println("Plain account due: Rs " + acc.getDue());
            }
        }
    }
}