package ch.bolkhuis.declabo.fund;

import javax.persistence.Entity;

@Entity
public class CreditEventFund extends EventFund {

    /**
     * Debit this CreditEventFund.
     *
     * Decrease the balance of this CreditEventFund by {@code amount}.
     *
     * @param amount the amount to subtract
     * @return the new balance
     */
    @Override
    public long debit(long amount) {
        // FIXME add overflow/underflow protection
        if (amount < 0L) throw new IllegalArgumentException("Cannot debit negative amount");
        balance -= amount;
        return balance;
    }

    /**
     * Credit this CreditEventFund.
     *
     * Increase the balance of this CreditEventFund by {@code amount}.
     *
     * @param amount the amount to add
     * @return the new balance
     */
    @Override
    public long credit(long amount) {
        // FIXME add overflow/underflow protection
        if (amount < 0L) throw new IllegalArgumentException("Cannot credit negative amount");
        balance += amount;
        return balance;
    }

    @Override
    protected String getIdString() {
        return "CreditEventFund";
    }

}
