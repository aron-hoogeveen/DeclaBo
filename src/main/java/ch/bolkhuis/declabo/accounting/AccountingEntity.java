package ch.bolkhuis.declabo.accounting;

public interface AccountingEntity {

    /**
     * Debits this {@code AccountingEntity} with the {@code amount}.
     *
     * @param amount the amount
     * @return the new balance
     */
    long debit(long amount);

    /**
     * Credits this {@code AccountingEntity} with the {@code amount}.
     *
     * @param amount the amount
     * @return the new balance
     */
    long credit(long amount);

    /**
     * Returns the name of this {@code AccountingEntity}.
     *
     * @return the name
     */
    String getName();
}
