package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.fund.Fund;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A standalone Transaction.
 */
public class BaseTransaction {

    private @Nullable Long id;
    private @NotNull Fund debtor;
    private @NotNull Fund creditor;
    private long amount;
    private @NotNull LocalDate date;
    private @NotNull String description;

    private BaseTransaction() {}

    /**
     * Constructs a new BaseTransaction.
     *
     * @param id the id
     * @param debtor the debtor fund
     * @param creditor the creditor fund
     * @param amount the amount
     * @param date the date
     * @param description the description
     * @throws NullPointerException if {@code debtor}, {@code creditor}, {@code date}, or
     *     {@code description} is null
     */
    public BaseTransaction(
            @Nullable Long id,
            @NotNull Fund debtor,
            @NotNull Fund creditor,
            long amount,
            @NotNull LocalDate date,
            @NotNull String description
    ) {
        this.id = id;
        this.debtor = Objects.requireNonNull(debtor, "Parameter 'debtor' must not be null");
        this.creditor = Objects.requireNonNull(creditor,
                "Parameter 'creditor' must not be null");
        this.amount = amount;
        this.date = Objects.requireNonNull(date, "Parameter 'data' must not be null");
        this.description = Objects.requireNonNull(description,
                "Parameter 'description' must not be null");
    }

    public @Nullable Long getId() {
        return id;
    }

    private void setId(@Nullable Long id) {
        this.id = id;
    }

    public @NotNull Fund getDebtor() {
        return debtor;
    }

    private void setDebtor(@NotNull Fund debtor) {
        this.debtor = Objects.requireNonNull(debtor);
    }

    public @NotNull Fund getCreditor() {
        return creditor;
    }

    private void setCreditor(@NotNull Fund creditor) {
        this.creditor = Objects.requireNonNull(creditor);
    }

    public long getAmount() {
        return amount;
    }

    private void setAmount(long amount) {
        this.amount = amount;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    private void setDate(@NotNull LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public @NotNull String getDescription() {
        return description;
    }

    private void setDescription(@NotNull String description) {
        this.description = Objects.requireNonNull(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseTransaction that = (BaseTransaction) o;

        if (amount != that.amount) return false;
        if (!debtor.equals(that.debtor)) return false;
        if (!creditor.equals(that.creditor)) return false;
        if (!date.equals(that.date)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = debtor.hashCode();
        result = 31 * result + creditor.hashCode();
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        result = 31 * result + date.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BaseTransaction{"
                + "description='" + description + '\''
                + '}';
    }
}
