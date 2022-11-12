package ch.bolkhuis.declabo.old.fund;

import ch.bolkhuis.declabo.old.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CreditEventFund extends CreditFund implements EventFund {

    private @NotNull Event event;

    /**
     * Construct a new {@code CreditFund}.
     *
     * @param id      the id
     * @param name    the name, must be non-null
     * @param balance the balance
     * @param event the event
     * @throws NullPointerException if {@code name} is null
     */
    public CreditEventFund(Long id, @NotNull String name, long balance, @NotNull Event event) {
        super(id, name, balance);
        this.event = Objects.requireNonNull(event, "Parameter 'event' must not be null");
    }

    @Override
    public @NotNull Event getEvent() {
        return this.event;
    }

    private void setEvent(@NotNull Event event) {
        this.event = Objects.requireNonNull(event);
    }
}
