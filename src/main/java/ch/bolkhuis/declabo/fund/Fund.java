package ch.bolkhuis.declabo.fund;

/**
 * A monetary fund.
 */
public interface Fund {

    /**
     * Gets the id of this {@code Fund}.
     *
     * @return the id
     */
    long getId();

    /**
     * Gets the balance of this {@code Fund}. The balance is returned in euro cents.
     *
     * @return the balance in eurocents
     */
    long getBalance();

    /**
     * Gets the name of this {@code Fund}.
     *
     * @return the name
     */
    String getName();

    /**
     * Debits the balance of this {@code Fund}.
     *
     * @param amount the amount to debit
     * @return the new balance
     * @throws IllegalArgumentException if amount is negative (optional)
     */
    long debit(long amount);

    /**
     * Credits the balance of this {@code Fund}.
     *
     * @param amount the amount to credit
     * @return the new balance
     * @throws IllegalArgumentException if amount is negative (optional)
     */
    long credit(long amount);
}
