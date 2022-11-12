package ch.bolkhuis.declabo.old.fund;

import ch.bolkhuis.declabo.old.event.Event;

public interface EventFund extends Fund {

    /**
     * Returns the event this fund is related to.
     *
     * @return the event
     */
    Event getEvent();
}
