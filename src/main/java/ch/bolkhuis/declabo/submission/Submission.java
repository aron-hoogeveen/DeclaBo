package ch.bolkhuis.declabo.submission;

import ch.bolkhuis.declabo.attachment.Attachment;
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
import javax.persistence.OneToMany;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
