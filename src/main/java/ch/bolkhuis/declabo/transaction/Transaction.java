package ch.bolkhuis.declabo.transaction;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.fund.Fund;
import ch.bolkhuis.declabo.submission.Submission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Transaction {

    private static final String settledErrorString = "Cannot change fields of a settled Transaction";

    @Id
    @GeneratedValue
    protected Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Fund debtor;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Fund creditor;

    protected long amount;

    @NotNull
    @Column(nullable = false)
    protected LocalDate date;

    @NotNull
    @Column(nullable = false)
    protected String description;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    protected Submission submission;

    @Nullable
    @ManyToOne(fetch = FetchType.EAGER)
    protected Event event;

    protected boolean settled;

    protected Transaction() {}

    /**
     * Create a new Transaction.
     */
    public Transaction(Fund debtor, Fund creditor, long amount, LocalDate date, String description,
                       @Nullable Submission submission, @Nullable Event event, boolean settled) {
        this.debtor = Objects.requireNonNull(debtor);
        this.creditor = Objects.requireNonNull(creditor);

        if (amount <= 0L) throw new IllegalArgumentException("Amount must not be zero or negative");

        this.amount = amount;
        this.date = Objects.requireNonNull(date);
        this.description = Objects.requireNonNull(description);
        this.submission = submission;
        this.event = event;
        this.settled = settled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Fund getDebtor() {
        return debtor;
    }

    /**
     * Set a new Debtor.
     *
     * @param debtor the new debtor
     * @throws IllegalStateException if this Transaction is already settled
     */
    public void setDebtor(@NotNull Fund debtor) {
        if (settled) throw new IllegalStateException(settledErrorString);

        this.debtor = Objects.requireNonNull(debtor);
    }

    public @NotNull Fund getCreditor() {
        return creditor;
    }

    /**
     * Set a new creditor.
     *
     * @param creditor the new creditor
     * @throws IllegalStateException if this Transaction is already settled
     */
    public void setCreditor(@NotNull Fund creditor) {
        if (settled) throw new IllegalStateException(settledErrorString);

        this.creditor = Objects.requireNonNull(creditor);
    }

    public long getAmount() {
        return amount;
    }

    /**
     * Set a new amount.
     *
     * @param amount the new amount
     * @throws IllegalStateException if this Transaction is already settled
     */
    public void setAmount(long amount) {
        if (settled) throw new IllegalStateException(settledErrorString);
        if (amount <= 0L) throw new IllegalArgumentException("Amount must not be zero or negative");

        this.amount = amount;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    /**
     * Set a new date.
     *
     * @param date the new date
     * @throws IllegalStateException if this Transaction is already settled
     */
    public void setDate(@NotNull LocalDate date) {
        if (settled) throw new IllegalStateException(settledErrorString);
        this.date = Objects.requireNonNull(date);
    }

    public @NotNull String getDescription() {
        return description;
    }

    /**
     * Set a new description.
     *
     * @param description the new description
     * @throws IllegalStateException if this Transaction is already settled
     */
    public void setDescription(@NotNull String description) {
        if (settled) throw new IllegalStateException(settledErrorString);
        this.description = Objects.requireNonNull(description);
    }

    public @Nullable Submission getSubmission() {
        return submission;
    }

    /**
     * Set a new submission.
     *
     * @param submission the new submission
     * @throws IllegalStateException if this Transaction is already settled
     */
    public void setSubmission(@Nullable Submission submission) {
        if (settled) throw new IllegalStateException(settledErrorString);
        this.submission = submission;
    }

    public @Nullable Event getEvent() {
        return event;
    }

    /**
     * Set a new event.
     *
     * @param event the new event
     * @throws IllegalStateException if this Transaction is already settled
     */
    public void setEvent(@Nullable Event event) {
        if (settled) throw new IllegalStateException(settledErrorString);
        this.event = event;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "@Transaction{date:" + date
                + ", description:" + description + ", isSettled:" + settled + "}";
    }

}
