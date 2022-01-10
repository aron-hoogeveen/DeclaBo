package ch.bolkhuis.declabo.accounting;

import org.jetbrains.annotations.NotNull;

/**
 * A debtor type accounting entity.
 */
public class Debtor extends AbstractAccountingEntity {

    /**
     * Constructs a new {@code Debtor}. Sets the initial balance.
     *
     * @param name the name
     * @param balance the initial balance
     */
    Debtor(@NotNull String name, long balance) {
        super(name, balance);
    }

    /**
     * Debits this {@code Debtor}. In other words, increases the balance of this {@code Debtor}.
     *
     * @param amount the amount
     * @return the new balance
     */
    @Override
    public long debit(long amount) {
        this.balance += amount;
        return this.balance;
    }

    /**
     * Credits this {@code Debtor}. In other words, decreases the balance of this {@code Debtor}.
     *
     * @param amount the amount
     * @return the new balance
     */
    @Override
    public long credit(long amount) {
        this.balance -= amount;
        return this.balance;
    }

    @Override
    public String toString() {
        return "Debtor{"
                + "name='" + name + '\''
                + ", balance=" + balance
                + '}';
    }
}
