package ch.bolkhuis.declabo.event;

import ch.bolkhuis.declabo.fund.EventFund;
import ch.bolkhuis.declabo.user.FundUser;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    protected String name;

    @NonNull
    @Column(nullable = false)
    protected LocalDate date;

    protected String description;

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY)
    protected Set<FundUser> attendants;

    @NonNull
    @OneToMany(fetch = FetchType.LAZY)
               // we can use OneToMany here, since there will probably never be an event with a
               // shitload of EventFunds
    protected Set<EventFund> funds;

    protected Event() {}

    /**
     * Creates an event with an initial {@code id}.
     *
     * @param id the id to set
     * @deprecated This method was only intended for use by the test classes of other classes which
     *             need this class.
     */
    @Deprecated
    public Event(long id) {
        this.id = id;
    }

    /**
     * Create a new Event with empty attendants and funds.
     *
     * @param name the name
     * @param date the (starting) date
     * @param description a descriptive message
     */
    public Event(@NonNull String name, @NonNull LocalDate date, @NonNull String description) {
        this.name = Objects.requireNonNull(name);
        this.date = Objects.requireNonNull(date);
        this.description = Objects.requireNonNull(description);
        this.attendants = new HashSet<>();
        this.funds = new HashSet<>();
    }

    /**
     * Create a new Event.
     *
     * @param name the name
     * @param date the (starting) date
     * @param description a descriptive message
     * @param attendants a set of all attendants
     * @param funds a set of all event funds belonging to this event
     */
    public Event(@NonNull String name, @NonNull LocalDate date, @NonNull String description, @NonNull Set<FundUser> attendants,
                 Set<EventFund> funds) {
        // FIXME attendants and funds should be modifiable
        this.name = Objects.requireNonNull(name);
        this.date = Objects.requireNonNull(date);
        this.description = Objects.requireNonNull(description);
        this.attendants = Objects.requireNonNull(attendants);
        this.funds = Objects.requireNonNull(funds);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @NonNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description);
    }

    @NonNull
    public Set<FundUser> getAttendants() {
        return attendants;
    }

    public void setAttendants(@NonNull Set<FundUser> attendants) {
        this.attendants = Objects.requireNonNull(attendants);
    }

    public void addAttendant(@NonNull FundUser attendant) {
        this.attendants.add(Objects.requireNonNull(attendant));
    }

    /**
     * Removes the attendant from the set of attendants.
     *
     * @param attendant the attendant to remove
     * @return {@code true} if the attendant was in the set and is now removed, {@code false}
     *         otherwise
     */
    public boolean removeAttendant(@NonNull FundUser attendant) {
        return attendants.remove(attendant);
    }

    public void addAttendants(@NonNull Set<FundUser> attendants) {
        // FIXME check that there are no null-valued attendants
        this.attendants.addAll(attendants);
    }

    @NonNull
    public Set<EventFund> getFunds() {
        return funds;
    }

    public void setFunds(@NonNull Set<EventFund> funds) {
        this.funds = Objects.requireNonNull(funds);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return name.equals(event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "@Event{name:" + name + ", date:" + date + "}";
    }

}
