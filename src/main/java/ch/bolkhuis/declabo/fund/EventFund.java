package ch.bolkhuis.declabo.fund;

import ch.bolkhuis.declabo.event.Event;

public interface EventFund extends Fund {

    /**
     * Returns the event this fund is related to.
     *
     * @return the event
     */
    Event getEvent();
}
