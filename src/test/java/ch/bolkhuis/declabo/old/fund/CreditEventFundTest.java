package ch.bolkhuis.declabo.old.fund;

import ch.bolkhuis.declabo.old.event.Event;
import ch.bolkhuis.declabo.old.event.SimpleEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

public class CreditEventFundTest {

    private static final transient Long ID = 0L;
    private static final transient String NAME = "Gerrit";
    private static final transient Long BALANCE = 23487L;
    private static final transient Event EVENT = new SimpleEvent(0L,
            LocalDate.of(1999, 2, 3), "event",
            "description", Set.of(), Set.of());

    @Test
    public void test_constructor() {
        new CreditEventFund(ID, NAME, BALANCE, EVENT);
        Assertions.assertThrows(NullPointerException.class, () ->
                new CreditEventFund(ID, NAME, BALANCE, null)
        );
    }

    @Test
    public void test_getters() {
        CreditEventFund creditEventFund = new CreditEventFund(ID, NAME, BALANCE, EVENT);
        Assertions.assertEquals(EVENT, creditEventFund.getEvent());
    }
}
