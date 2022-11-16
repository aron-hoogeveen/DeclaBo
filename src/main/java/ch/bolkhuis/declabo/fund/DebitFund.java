package ch.bolkhuis.declabo.fund;

import javax.persistence.Entity;

@Entity
public class DebitFund extends Fund {

    protected static String toStringID = "DebitFund";

    protected DebitFund() {}

    public DebitFund(String name, long balance) {
        super(name, balance);
    }

    /**
     * Debit this DebitFund.
     *
     * increases the balance of this DebitFund by {@code amount}.
     *
     * @param amount the amount to add
     * @return the new balance
     */
    @Override
    public long debit(long amount) {
        // FIXME add overflow/underflow protection
        if (amount < 0L) throw new IllegalArgumentException("Cannot debit negative amount");
        balance += amount;
        return balance;
    }

    /**
     * Credit this DebitFund.
     *
     * Decrease the balance of this DebitFund by {@code amount}.
     *
     * @param amount the amount to subtract
     * @return the new balance
     */
    @Override
    public long credit(long amount) {
        // FIXME add overflow/underflow protection
        if (amount < 0L) throw new IllegalArgumentException("Cannot credit negative amount");
        balance -= amount;
        return balance;
    }

    @Override
    protected String getIdString() {
        return "DebitFund";
    }

}
