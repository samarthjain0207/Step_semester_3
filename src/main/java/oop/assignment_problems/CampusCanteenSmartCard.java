import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

interface PricingPlan {
    String getName();
    BigDecimal price(BigDecimal basePrice);
}

class DayScholarPlan implements PricingPlan {
    public String getName() { return "Day Scholar"; }
    public BigDecimal price(BigDecimal base) { return base.setScale(2, RoundingMode.HALF_UP); }
}

class HostellerPlan implements PricingPlan {
    public String getName() { return "Hosteller"; }
    public BigDecimal price(BigDecimal base) { return base.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP); }
}

class StaffPlan implements PricingPlan {
    public String getName() { return "Staff"; }
    public BigDecimal price(BigDecimal base) { return base.multiply(new BigDecimal("0.80")).setScale(2, RoundingMode.HALF_UP); }
}

enum TransactionType { TOP_UP, PURCHASE, REFUND }
enum CardState { ACTIVE, BLOCKED }

class Transaction {
    private final TransactionType type;
    private final BigDecimal amount;      // signed: + credit, - debit
    private final String description;
    Transaction(TransactionType type, BigDecimal amount, String description) {
        this.type = type; this.amount = amount; this.description = description;
    }
    BigDecimal getAmount() { return amount; }
}

class Purchase {
    private final String item;
    private final BigDecimal charged;
    private boolean refunded;
    Purchase(String item, BigDecimal charged) { this.item = item; this.charged = charged; }
    String getItem() { return item; }
    BigDecimal getCharged() { return charged; }
    boolean isRefunded() { return refunded; }
    void markRefunded() { refunded = true; }
}

class SmartCard {
    private static final BigDecimal MIN_TOP_UP = new BigDecimal("100");
    private static final BigDecimal MAX_BALANCE = new BigDecimal("5000");

    private final String id;
    private final PricingPlan plan;
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<Purchase> purchases = new ArrayList<>();
    private CardState state = CardState.ACTIVE;

    SmartCard(String id, PricingPlan plan) { this.id = id; this.plan = plan; }

    static String rs(BigDecimal v) { return "\u20B9" + String.format(Locale.US, "%.2f", v); }

    // DERIVED, never stored: balance == sum of transactions holds by construction
    BigDecimal getBalance() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Transaction t : transactions) sum = sum.add(t.getAmount());
        return sum;
    }

    boolean topUp(BigDecimal amount) {
        if (state == CardState.BLOCKED) { System.out.println("Top-up failed: Card " + id + " is blocked."); return false; }
        if (amount.compareTo(MIN_TOP_UP) < 0) { System.out.println("Top-up failed: Minimum top-up is " + rs(MIN_TOP_UP) + "."); return false; }
        if (getBalance().add(amount).compareTo(MAX_BALANCE) > 0) {
            System.out.println("Top-up failed: Balance cannot exceed " + rs(MAX_BALANCE) + "."); return false;
        }
        transactions.add(new Transaction(TransactionType.TOP_UP, amount, "Top-up"));
        System.out.println(id + " topped up with " + rs(amount) + ". Balance: " + rs(getBalance()) + ".");
        return true;
    }

    Purchase purchase(String item, BigDecimal basePrice) {
        if (state == CardState.BLOCKED) { System.out.println("Purchase failed: Card " + id + " is blocked."); return null; }
        if (basePrice.signum() <= 0) { System.out.println("Purchase failed: Price must be positive."); return null; }
        BigDecimal charge = plan.price(basePrice);   // polymorphic pricing
        BigDecimal balance = getBalance();
        if (balance.compareTo(charge) < 0) {
            System.out.println("Purchase failed: Insufficient balance (required " + rs(charge) + ", available " + rs(balance) + ").");
            return null;
        }
        transactions.add(new Transaction(TransactionType.PURCHASE, charge.negate(), item));
        Purchase p = new Purchase(item, charge);
        purchases.add(p);
        System.out.println(item + " purchased for " + rs(charge) + ". Balance: " + rs(getBalance()) + ".");
        return p;
    }

    // Refund returns the exact amount charged, once only.
    // (Only top-ups and purchases are blocked while a card is blocked.)
    boolean refund(Purchase p) {
        if (p == null || !purchases.contains(p)) { System.out.println("Refund rejected: Unknown purchase."); return false; }
        if (p.isRefunded()) { System.out.println("Refund rejected: " + p.getItem() + " has already been refunded."); return false; }
        p.markRefunded();
        transactions.add(new Transaction(TransactionType.REFUND, p.getCharged(), "Refund: " + p.getItem()));
        System.out.println("Refund of " + rs(p.getCharged()) + " for " + p.getItem() + " processed. Balance: " + rs(getBalance()) + ".");
        return true;
    }

    void block()   { state = CardState.BLOCKED; System.out.println("Card " + id + " blocked."); }
    void unblock() { state = CardState.ACTIVE;  System.out.println("Card " + id + " unblocked."); }

    String miniStatement() {
        StringBuilder sb = new StringBuilder("Mini-statement for " + id + ": ");
        for (int i = 0; i < transactions.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(String.format(Locale.US, "%+.2f", transactions.get(i).getAmount()));
        }
        return sb.append(" = ").append(rs(getBalance())).append(".").toString();
    }
}

public class CampusCanteenSmartCard {
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());

        card.topUp(BigDecimal.valueOf(500));
        Purchase thali = card.purchase("Veg Thali", BigDecimal.valueOf(120));
        card.purchase("Cold Coffee", BigDecimal.valueOf(60));
        card.purchase("Items", BigDecimal.valueOf(400));      // insufficient balance
        card.refund(thali);
        card.refund(thali);                                    // already refunded
        System.out.println(card.miniStatement());
    }
}
