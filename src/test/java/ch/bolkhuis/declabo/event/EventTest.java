package ch.bolkhuis.declabo.event;

import ch.bolkhuis.declabo.user.FundUser;
import ch.bolkhuis.declabo.user.FundUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("PMD")
public class EventTest {

    @Test
    public void should_set_all_fields_on_construction() {
        String name = "My Event";
        LocalDate date = LocalDate.now();
        String description = "my description";
        State state = State.OPEN;
        Event event = new Event(name, date, description, state);

        assertThat(event).hasFieldOrPropertyWithValue("name", name);
        assertThat(event).hasFieldOrPropertyWithValue("date", date);
        assertThat(event).hasFieldOrPropertyWithValue("description", description);
        assertThat(event).hasFieldOrPropertyWithValue("state", state);
    }

    @Test
    public void should_save_attendant_when_added() {
        Event event = new Event("name", LocalDate.now(), "description", State.OPEN);
        FundUser attendant = FundUserTest.getTestCreditFundUser();
        event.addAttendant(attendant);

        assertThat(event.getAttendants()).contains(attendant);
    }

    @Test
    public void should_reject_null_valued_attendants() {
        Event event = getTestEvent();
        assertThrows(NullPointerException.class, () -> event.setAttendants(null));
        Set<FundUser> setWithNull = new HashSet<>();
        setWithNull.add(null);
        assertThrows(NullPointerException.class, () -> event.setAttendants(setWithNull));

        FundUser user = FundUserTest.getTestCreditFundUser();
        Set<FundUser> legalSet = new HashSet<>();
        legalSet.add(user);
        assertDoesNotThrow(() -> event.setAttendants(legalSet));
    }

    @Test
    public void should_return_null_when_removed_attendant_was_not_in_set() {
        Event event = getTestEvent();
        FundUser attendant = FundUserTest.getTestCreditFundUser();

        assertThat(event.removeAttendant(attendant)).isEqualTo(false);
        assertThat(event.getAttendants()).doesNotContain(attendant);
    }

    @Test
    public void should_return_true_if_attendant_removed() {
        Event event = getTestEvent();
        FundUser attendant = FundUserTest.getTestCreditFundUser();
        event.addAttendant(attendant);

        assertThat(event.removeAttendant(attendant)).isEqualTo(true);
        assertThat(event.getAttendants()).doesNotContain(attendant);
    }

    public static Event getTestEvent() {
        return new Event("Test Event", LocalDate.now(), "This is a test event.", State.OPEN);
    }

}
