package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.user.FundUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Submission {

    @Id
    @GeneratedValue
    protected Long id;

    @NotNull
    @Column(nullable = false)
    protected LocalDate createdOn;

    @NotNull
    @Column(nullable = false)
    protected LocalDate date;

    @ManyToOne
    protected FundUser payedBy;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Event event;

    @NotNull
    @Column(nullable = false)
    protected String name;

    @Nullable
    protected String remarks;

    /**
     * Boolean that signals that the CFO has processed this submission and converted it into transactions.
     */
    protected boolean processed;

    protected boolean settled;

    protected Submission() {}

    public Submission(@NotNull LocalDate createdOn, @NotNull LocalDate date, FundUser payedBy,
                      @NotNull Event event, @NotNull String name,
                      @Nullable String remarks, boolean processed, boolean settled) {
        this.createdOn = Objects.requireNonNull(createdOn);
        this.date = Objects.requireNonNull(date);
        this.payedBy = payedBy;
        this.event = Objects.requireNonNull(event);
        this.name = Objects.requireNonNull(name);
        this.remarks = remarks;
        this.processed = processed;
        this.settled = settled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (!isChangeable()) throw new IllegalStateException();
        this.id = id;
    }

    public @NotNull LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(@NotNull LocalDate createdOn) {
        if (!isChangeable()) throw new IllegalStateException();
        this.createdOn = createdOn;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate date) {
        if (!isChangeable()) throw new IllegalStateException();
        this.date = date;
    }

    public FundUser getPayedBy() {
        return payedBy;
    }

    public void setPayedBy(FundUser payedBy) {
        if (!isChangeable()) throw new IllegalStateException();
        this.payedBy = payedBy;
    }

    public @NotNull Event getEvent() {
        return event;
    }

    public void setEvent(@NotNull Event event) {
        if (!isChangeable()) throw new IllegalStateException();
        this.event = event;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        if (!isChangeable()) throw new IllegalStateException();
        this.name = name;
    }

    public @Nullable String getRemarks() {
        return remarks;
    }

    public void setRemarks(@Nullable String remarks) {
        if (!isChangeable()) throw new IllegalStateException();
        this.remarks = remarks;
    }

    public boolean isProcessed() {
        return processed;
    }

    /**
     * Signals if the CFO has already processed this Submission. I.e. he/she created all the
     * transactions for this submission.
     *
     * @param processed the new value for processed
     */
    public void setProcessed(boolean processed) {
        if (!isChangeable()) throw new IllegalStateException();
        this.processed = processed;
    }

    public boolean isSettled() {
        return settled;
    }

    /**
     * Set the state of this {@code Submission} to 'settled'.
     *
     * The submission can only be set to settled if it is also marked as 'processed'.
     *
     * @param settled the new state
     */
    public void setSettled(boolean settled) {
        if (settled && !processed) throw new IllegalStateException("Cannot set a non-processed Submission to 'settled'");
        this.settled = settled;
    }

    /**
     * Fields can only be changed when {@code settled} is {@code false}.
     *
     * @return whether this object is allowed to be modified.
     */
    private boolean isChangeable() {
        return !settled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return createdOn.equals(that.createdOn) && date.equals(that.date) && Objects.equals(payedBy, that.payedBy) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdOn, date, payedBy, name);
    }

    public static Submission getTestSubmission() {
        return new Submission(LocalDate.now(), LocalDate.now(), FundUser.getTestUser(),
                Event.getTestEvent(), "Test Submission", "No remarks",
                false, false);
    }
}
