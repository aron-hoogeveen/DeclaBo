package ch.bolkhuis.declabo.event;

import ch.bolkhuis.declabo.user.FundUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD")
public class EventTest {

    @Test
    public void should_set_all_fields_on_construction() {
        String name = "My Event";
        LocalDate date = LocalDate.now();
        String description = "my description";
        Event event = new Event(name, date, description);

        assertThat(event).hasFieldOrPropertyWithValue("name", name);
        assertThat(event).hasFieldOrPropertyWithValue("date", date);
        assertThat(event).hasFieldOrPropertyWithValue("description", description);
    }

    @Test
    public void should_save_attendant_when_added() {
        Event event = new Event("name", LocalDate.now(), "description");
        FundUser attendant = FundUser.getTestUser();
        event.addAttendant(attendant);

        assertThat(event.getAttendants()).contains(attendant);
    }

    // TODO add test for Event#addAttendants(Set) that disallows null-valued input (both in the list and the list itself)

    // TODO add test for Event#addAttendant(FundUser) and Event#addAttendants(Set). They must not fail when adding a FundUser which is already present

    @Test
    public void should_return_null_when_removed_attendant_was_not_in_set() {
        Event event = new Event("name", LocalDate.now(), "description");
        FundUser attendant = FundUser.getTestUser();

        assertThat(event.removeAttendant(attendant)).isEqualTo(false);
        assertThat(event.getAttendants()).doesNotContain(attendant);
    }

    @Test
    public void should_return_true_if_attendant_removed() {
        Event event = new Event("name", LocalDate.now(), "description");
        FundUser attendant = FundUser.getTestUser();
        event.addAttendant(attendant);

        assertThat(event.removeAttendant(attendant)).isEqualTo(true);
        assertThat(event.getAttendants()).doesNotContain(attendant);
    }

}
