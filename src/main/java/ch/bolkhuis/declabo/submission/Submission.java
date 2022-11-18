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
    protected FundUser paidBy;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Event event;

    @NotNull
    @Column(nullable = false)
    protected String name;

    @Nullable
    protected String remarks;

    @NotNull
    protected Status status;

    // state changes
    protected static boolean[][] stateChanges = new boolean[Status.values().length][Status.values().length];

    static {
        // By default, all state changes are not allowed. Set which ones are allowed here
        stateChanges[Status.PENDING.getValue()][Status.PENDING.getValue()] = true;
        stateChanges[Status.PENDING.getValue()][Status.IN_PROGRESS.getValue()] = true;
        stateChanges[Status.PENDING.getValue()][Status.REJECTED.getValue()] = true;

        stateChanges[Status.BEING_CREATED.getValue()][Status.BEING_CREATED.getValue()] = true;
        stateChanges[Status.BEING_CREATED.getValue()][Status.IN_PROGRESS.getValue()] = true;

        stateChanges[Status.IN_PROGRESS.getValue()][Status.IN_PROGRESS.getValue()] = true;
        stateChanges[Status.IN_PROGRESS.getValue()][Status.REJECTED.getValue()] = true;
        stateChanges[Status.IN_PROGRESS.getValue()][Status.PROCESSED.getValue()] = true;

        stateChanges[Status.REJECTED.getValue()][Status.REJECTED.getValue()] = true;
        stateChanges[Status.REJECTED.getValue()][Status.IN_PROGRESS.getValue()] = true;

        stateChanges[Status.PROCESSED.getValue()][Status.PROCESSED.getValue()] = true;
        stateChanges[Status.PROCESSED.getValue()][Status.IN_PROGRESS.getValue()] = true;
        stateChanges[Status.PROCESSED.getValue()][Status.SETTLED.getValue()] = true;

        stateChanges[Status.SETTLED.getValue()][Status.SETTLED.getValue()] = true;
    }

    protected Submission() {}

    /**
     * Constructs a new Submission.
     *
     * @param createdOn the date on which this submission was created
     * @param date the date on which the transactions of this submission happened
     * @param paidBy the person/entity who/that paid for this submission
     * @param event the event this submission belongs to. May be null
     * @param name a describing name for this submission
     * @param remarks any remarks on this submission
     * @param status the current status of this submission
     */
    public Submission(@NotNull LocalDate createdOn, @NotNull LocalDate date, FundUser paidBy,
                      @NotNull Event event, @NotNull String name,
                      @Nullable String remarks, @NotNull Status status) {
        this.createdOn = Objects.requireNonNull(createdOn);
        this.date = Objects.requireNonNull(date);
        this.paidBy = paidBy;
        this.event = Objects.requireNonNull(event);
        this.name = Objects.requireNonNull(name);
        this.remarks = remarks;
        this.status = Objects.requireNonNull(status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(@NotNull LocalDate createdOn) {
        if (status != Status.BEING_CREATED) throw new IllegalStateException();
        this.createdOn = createdOn;
    }

    public @NotNull LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate date) {
        if (!modifiable()) throw new IllegalStateException();
        this.date = date;
    }

    public FundUser getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(FundUser payedBy) {
        if (!modifiable()) throw new IllegalStateException();
        this.paidBy = payedBy;
    }

    public @NotNull Event getEvent() {
        return event;
    }

    public void setEvent(@NotNull Event event) {
        if (!modifiable()) throw new IllegalStateException();
        this.event = event;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        if (!modifiable()) throw new IllegalStateException();
        this.name = name;
    }

    public @Nullable String getRemarks() {
        return remarks;
    }

    public void setRemarks(@Nullable String remarks) {
        if (!modifiable()) throw new IllegalStateException();
        this.remarks = remarks;
    }

    public @NotNull Status getStatus() {
        return status;
    }

    /**
     * Set the new Status.
     *
     * A status change is only possible if it is an allowed state change.
     *
     * @param newStatus the new status
     */
    public void setStatus(@NotNull Status newStatus) {
        if (!stateChanges[status.getValue()][newStatus.getValue()])
            throw new IllegalStateException("This state change is not allowed: " + status + " to " + newStatus);
        this.status = Objects.requireNonNull(newStatus);
    }

    /**
     * Returns whether all modifiable fields are allowed to change in the current state.
     */
    private boolean modifiable() {
        return status == Status.PENDING || status == Status.BEING_CREATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return createdOn.equals(that.createdOn) && date.equals(that.date) && Objects.equals(paidBy, that.paidBy) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdOn, date, paidBy, name);
    }

}
