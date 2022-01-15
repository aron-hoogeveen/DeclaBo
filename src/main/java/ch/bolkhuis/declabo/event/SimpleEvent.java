package ch.bolkhuis.declabo.event;

import ch.bolkhuis.declabo.fund.EventFund;
import ch.bolkhuis.declabo.user.FundUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SimpleEvent implements Event {

    private @Nullable Long id;
    private @NotNull LocalDate date;
    private @NotNull String name;
    private @NotNull String description;
    private @NotNull Set<FundUser> attendants;
    private @NotNull Set<EventFund> funds;

    /**
     * Constructs a new SimpleEvent.
     *
     * <p>Both the set of {@code attendants} and {@code funds} will be copied over to unmodifiable
     * sets.
     *
     * @param id the id
     * @param date the date
     * @param name the name
     * @param description the description
     * @param attendants the attendants
     * @param funds the funds
     * @throws NullPointerException if {@code date}, {@code name}, {@code description},
     *     {@code attendants}, or {@code funds} is null
     */
    public SimpleEvent(
            @Nullable Long id,
            @NotNull LocalDate date,
            @NotNull String name,
            @NotNull String description,
            @NotNull Set<FundUser> attendants,
            @NotNull Set<EventFund> funds
    ) {
        this.id = id;
        this.date = Objects.requireNonNull(date, "Parameter 'date' must not be null");
        this.name = Objects.requireNonNull(name, "Parameter 'name' must not be null");
        this.description = Objects.requireNonNull(description,
                "Parameter 'description' must not be null");
        Objects.requireNonNull(attendants,
                "Parameter 'attendants' must not be null");
        this.attendants = Set.copyOf(attendants);
        Objects.requireNonNull(funds, "Parameter 'funds' must not be null");
        this.funds = Set.copyOf(funds);
    }

    @Override
    public @Nullable Long getId() {
        return id;
    }

    private void setId(@Nullable Long id) {
        this.id = id;
    }

    @Override
    public @NotNull LocalDate getDate() {
        return date;
    }

    private void setDate(@NotNull LocalDate date) {
        this.date = date;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    private void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }

    private void setDescription(@NotNull String description) {
        this.description = description;
    }

    @Override
    public @NotNull Set<FundUser> getAttendants() {
        return attendants;
    }

    private void setAttendants(@NotNull Set<FundUser> attendants) {
        this.attendants = Set.copyOf(Objects.requireNonNull(attendants));
    }

    @Override
    public @NotNull Set<EventFund> getFunds() {
        return funds;
    }

    private void setFunds(@NotNull Set<EventFund> funds) {
        this.funds = Set.copyOf(Objects.requireNonNull(funds));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleEvent that = (SimpleEvent) o;

        if (!date.equals(that.date)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
