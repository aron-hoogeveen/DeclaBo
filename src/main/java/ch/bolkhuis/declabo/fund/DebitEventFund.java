package ch.bolkhuis.declabo.fund;

import javax.persistence.Entity;

@Entity
public class DebitEventFund extends EventFund {

    /**
     * Debit this DebitEventFund.
     *
     * increases the balance of this DebitEventFund by {@code amount}.
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
     * Credit this DebitEventFund.
     *
     * Decrease the balance of this DebitEventFund by {@code amount}.
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
        return "DebitEventFund";
    }

}
