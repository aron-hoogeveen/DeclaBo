package ch.bolkhuis.declabo.event;

import ch.bolkhuis.declabo.fund.EventFund;
import ch.bolkhuis.declabo.user.FundUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// TODO add validation constraints
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    protected String name;

    @NotNull
    @Column(nullable = false)
    protected LocalDate date;

    protected String description;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    protected Set<FundUser> attendants;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY)
    // we can use OneToMany here, since there will probably never be an event with a
    // shitload of EventFunds
    protected Set<EventFund> funds;

    /**
     * The current state of the event.
     */
    @NotNull
    protected State state;

    protected Event() {}

    /**
     * Create a new Event with empty attendants and funds.
     *
     * @param name the name
     * @param date the (starting) date
     * @param description a descriptive message
     * @param state the current state of the event
     */
    public Event(@NotNull String name, @NotNull LocalDate date, @NotNull String description,
                 @NotNull State state) {
        this.name = Objects.requireNonNull(name);
        this.date = Objects.requireNonNull(date);
        this.description = Objects.requireNonNull(description);
        this.attendants = new HashSet<>();
        this.funds = new HashSet<>();
        this.state = Objects.requireNonNull(state);
    }

    /**
     * Create a new Event.
     *
     * For {@code attendants} and {@code funds} new HashSets are created to ensure modifiability of
     * the sets.
     *
     * @param name the name
     * @param date the (starting) date
     * @param description a descriptive message
     * @param attendants a set of all attendants
     * @param funds a set of all event funds belonging to this event
     * @param state the current state of the event
     */
    public Event(@NotNull String name, @NotNull LocalDate date, @NotNull String description,
                 @NotNull State state, @NotNull Set<FundUser> attendants, Set<EventFund> funds) {
        this.name = Objects.requireNonNull(name);
        this.date = Objects.requireNonNull(date);
        this.description = Objects.requireNonNull(description);
        this.attendants = new HashSet<>(Objects.requireNonNull(attendants));
        this.funds = new HashSet<>(Objects.requireNonNull(funds));
        this.state = Objects.requireNonNull(state);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @NotNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Objects.requireNonNull(description);
    }

    public @NotNull State getState() {
        return state;
    }

    public void setState(@NotNull State state) {
        this.state = Objects.requireNonNull(state);
    }

    @NotNull
    public Set<FundUser> getAttendants() {
        return attendants;
    }

    /**
     * Sets the attendants.
     *
     * The set must not contain a null value not be null itself.
     *
     * @param attendants the set of attendants
     * @throws NullPointerException if {@code attendants} contains a null value or is null
     */
    public void setAttendants(@NotNull Set<FundUser> attendants) {
        Objects.requireNonNull(attendants);
        if (attendants.contains(null))
            throw new NullPointerException("Null valued attendants are not allowed");

        this.attendants = Objects.requireNonNull(attendants);
    }

    public void addAttendant(@NotNull FundUser attendant) {
        this.attendants.add(Objects.requireNonNull(attendant));
    }

    /**
     * Removes the attendant from the set of attendants.
     *
     * @param attendant the attendant to remove
     * @return {@code true} if the attendant was in the set and is now removed, {@code false}
     *         otherwise
     */
    public boolean removeAttendant(@NotNull FundUser attendant) {
        return attendants.remove(Objects.requireNonNull(attendant));
    }

    /**
     * Adds all attendants to the set of attendants.
     *
     * The set must not contain null values.
     *
     * @param attendants the attendants to add
     * @throws NullPointerException if {@code attendants} is null or contains a null value
     */
    public void addAttendants(@NotNull Set<FundUser> attendants) {
        Objects.requireNonNull(attendants);
        if (attendants.contains(null))
            throw new NullPointerException("The set must not contain null valued elements");

        this.attendants.addAll(attendants);
    }

    @NotNull
    public Set<EventFund> getFunds() {
        return funds;
    }

    public void setFunds(@NotNull Set<EventFund> funds) {
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
