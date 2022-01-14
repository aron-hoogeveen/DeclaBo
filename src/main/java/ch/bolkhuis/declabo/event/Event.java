package ch.bolkhuis.declabo.event;

import ch.bolkhuis.declabo.fund.EventFund;
import ch.bolkhuis.declabo.fund.Fund;
import ch.bolkhuis.declabo.user.FundUser;

import java.time.LocalDate;
import java.util.Set;

/**
 * An event such as a vacation.
 */
public interface Event {

    /**
     * Gets the id of this {@code event}.
     *
     * @return the id
     */
    Long getId();

    /**
     * Gets the date of this {@code event}.
     *
     * @return the date
     */
    LocalDate getDate();

    /**
     * Gets the name of this {@code event}.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets the description of this {@code event}.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Gets the attendants of this {@code event}.
     *
     * @return the attendants
     */
    Set<FundUser> getAttendants();

    /**
     * Gets the funds related to this {@code event}.
     *
     * @return the funds
     */
    Set<EventFund> getFunds();
}
