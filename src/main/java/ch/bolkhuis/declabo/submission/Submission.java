package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.event.Event;
import ch.bolkhuis.declabo.user.FundUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.CascadeType;
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

    @ManyToOne(cascade = CascadeType.ALL) // FIXME this is only for testing purposes
    protected FundUser payedBy;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL) // FIXME this is only for testing purposes
    @JoinColumn(nullable = false)
    protected Event event;

    @NotNull
    @Column(nullable = false)
    protected String name;

    @Nullable
    protected String remarks;

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
        this.id = id;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public FundUser getPayedBy() {
        return payedBy;
    }

    public void setPayedBy(FundUser payedBy) {
        this.payedBy = payedBy;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
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
