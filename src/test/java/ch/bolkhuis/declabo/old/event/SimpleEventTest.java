package ch.bolkhuis.declabo.old.event;

import ch.bolkhuis.declabo.old.fund.EventFund;
import ch.bolkhuis.declabo.old.user.FundUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SimpleEventTest {

    private static final transient Long ID = 1L;
    private static final transient LocalDate DATE = LocalDate.of(2022, 1, 14);
    private static final transient String NAME = "Huischvakantie";
    private static final transient String DESCRIPTION = "De vakantie naar Coo";
    private static final transient Set<FundUser> ATTENDANTS = new HashSet<>();
    private static final transient Set<EventFund> FUNDS = new HashSet<>();

    @Test
    public void test_constructor() {
        new SimpleEvent(ID, DATE, NAME, DESCRIPTION, ATTENDANTS, FUNDS);
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleEvent(ID, null, NAME, DESCRIPTION, ATTENDANTS, FUNDS)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleEvent(ID, DATE, null, DESCRIPTION, ATTENDANTS, FUNDS)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleEvent(ID, DATE, NAME, null, ATTENDANTS, FUNDS)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleEvent(ID, DATE, NAME, DESCRIPTION, null, FUNDS)
        );
        Assertions.assertThrows(NullPointerException.class, () ->
                new SimpleEvent(ID, DATE, NAME, DESCRIPTION, ATTENDANTS, null)
        );
    }

    @Test
    public void test_getters() {
        SimpleEvent simpleEvent = new SimpleEvent(ID, DATE, NAME, DESCRIPTION, ATTENDANTS, FUNDS);
        Assertions.assertEquals(ID, simpleEvent.getId());
        Assertions.assertEquals(DATE, simpleEvent.getDate());
        Assertions.assertEquals(NAME, simpleEvent.getName());
        Assertions.assertEquals(DESCRIPTION, simpleEvent.getDescription());
        Assertions.assertEquals(ATTENDANTS, simpleEvent.getAttendants());
        Assertions.assertEquals(FUNDS, simpleEvent.getFunds());
    }

    @Test
    public void test_equals() {
        SimpleEvent simpleEvent1 = new SimpleEvent(ID, DATE, NAME, DESCRIPTION, ATTENDANTS, FUNDS);
        SimpleEvent simpleEvent2 = new SimpleEvent(ID, DATE, NAME, DESCRIPTION, ATTENDANTS, FUNDS);
        SimpleEvent simpleEvent3 = new SimpleEvent(ID, DATE, "Different Name", DESCRIPTION,
                ATTENDANTS, FUNDS);
        SimpleEvent simpleEvent4 = new SimpleEvent(ID, LocalDate.of(1900, 1, 1), NAME,
                DESCRIPTION, ATTENDANTS, FUNDS);

        Assertions.assertEquals(simpleEvent1, simpleEvent2);
        Assertions.assertNotEquals(simpleEvent1, simpleEvent3);
        Assertions.assertNotEquals(simpleEvent1, simpleEvent4);
    }
}
