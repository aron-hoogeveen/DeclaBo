package ch.bolkhuis.declabo.accounting;

import org.jetbrains.annotations.NotNull;

/**
 * A creditor type accounting entity.
 */
public class Creditor extends AbstractAccountingEntity {

    /**
     * Constructs a new {@code Creditor}. Sets the initial balance.
     *
     * @param name the name
     * @param balance the initial balance
     */
    Creditor(@NotNull String name, long balance) {
        super(name, balance);
    }

    /**
     * Debits this {@code Creditor}. In other words, decreases the balance of this {@code Creditor}.
     *
     * @param amount the amount
     * @return the new balance
     */
    @Override
    public long debit(long amount) {
        this.balance -= amount;
        return this.balance;
    }

    /**
     * Credits this {@code Creditor}. In other words, increases the balance of this
     * {@code Creditor}.
     *
     * @param amount the amount
     * @return the new balance
     */
    @Override
    public long credit(long amount) {
        this.balance += amount;
        return this.balance;
    }

    @Override
    public String toString() {
        return "Creditor{"
                + "name='" + name + '\''
                + ", balance=" + balance
                + '}';
    }
}
