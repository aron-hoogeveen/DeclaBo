package ch.bolkhuis.declabo.fund;

import ch.bolkhuis.declabo.event.Event;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public abstract class EventFund extends Fund {

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "event_id")
    protected Event event;

    protected EventFund() {}

    public EventFund(String name, long balance, Event event) {
        super(name, balance);
        this.event = Objects.requireNonNull(event);
    }

    @NonNull
    public Event getEvent() {
        return event;
    }

    public void setEvent(@NonNull Event event) {
        this.event = Objects.requireNonNull(event);
    }

    public abstract long debit(long amount);

    public abstract long credit(long amount);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EventFund eventFund = (EventFund) o;
        return event.equals(eventFund.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), event);
    }

    @Override
    protected String getIdString() {
        return "EventFund";
    }

}
