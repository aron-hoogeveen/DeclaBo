package ch.bolkhuis.declabo.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("PMD")
@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    EventRepository repository;

    @Test
    public void should_find_no_events_if_repository_is_empty() {
        Iterable<Event> events = repository.findAll();

        assertThat(events).isEmpty();
    }

    @Test
    public void should_store_an_event() {
        String name = "My Event";
        LocalDate date = LocalDate.now();
        String description = "my description";
        Event event = repository.save(new Event(name, date, description));

        assertThat(event).hasFieldOrPropertyWithValue("name", name);
        assertThat(event).hasFieldOrPropertyWithValue("date", date);
        assertThat(event).hasFieldOrPropertyWithValue("description", description);
    }

    @Test
    public void should_reject_save_on_duplicate_name() {
        String name = "My Event";

        Event e1 = new Event(name, LocalDate.of(2022, 1, 1), "yes");
        Event e2 = new Event(name, LocalDate.of(1099, 1, 1), "no");
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAllAndFlush(List.of(e1, e2)));
    }

}
