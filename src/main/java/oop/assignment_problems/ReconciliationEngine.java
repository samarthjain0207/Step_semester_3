package oop.assignment_problems;

public class ReconciliationEngine {

    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;

        double grandTotalSurgeFee = 0.0;

        // Use the smallest length to avoid mismatched-array errors
        int length = Math.min(
                accounts.length,
                Math.min(amounts.length, delayMinutesArray.length)
        );

        for (int i = 0; i < length; i++) {

            DeliveryAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            double surgeFee;

            if (account instanceof PremiumDeliveryAccount) {

                PremiumDeliveryAccount premium =
                        (PremiumDeliveryAccount) account;

                surgeFee =
                        premium.calculatePremiumSurgeFee(
                                delayMinutesArray[i]
                        );

                premiumCount++;

            } else {

                surgeFee =
                        account.calculateSurgeFee(
                                delayMinutesArray[i]
                        );

                regularCount++;
            }

            processed++;
            grandTotalSurgeFee += surgeFee;

            System.out.println(
                    account.studentId +
                    " -> Surge Fee: Rs " +
                    surgeFee
            );
        }

        System.out.println();
        System.out.println(processed + " processed");
        System.out.println(nullSkipped + " null skipped");
        System.out.println(premiumCount + " premium");
        System.out.println(regularCount + " regular");
        System.out.println(
                "Grand total surge fees = Rs " +
                grandTotalSurgeFee
        );
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {

            new PremiumDeliveryAccount(
                    "STU001",
                    500
            ),

            null,

            new DeliveryAccount(
                    "STU002",
                    300
            )
        };

        double[] amounts = {
            500,
            400,
            300
        };

        int[] delayMinutesArray = {
            10,
            5,
            0
        };

        processBatch(
                accounts,
                amounts,
                delayMinutesArray
        );
    }
}