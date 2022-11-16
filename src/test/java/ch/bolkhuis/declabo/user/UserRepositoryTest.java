package ch.bolkhuis.declabo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("PMD")
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    public void should_find_no_users_if_repo_is_empty() {
        Iterable<User> users = repository.findAll();
    }

    @Test
    public void should_store_a_user() {
        String email = "aron@bolkhuis.ch";
        String name = "Aron";
        String surname = "Hoogeveen";
        int room = 101;
        User user = repository.save(new User(email, name, surname, room));

        assertThat(user).hasFieldOrPropertyWithValue("email", email);
        assertThat(user).hasFieldOrPropertyWithValue("name", name);
        assertThat(user).hasFieldOrPropertyWithValue("surname", surname);
        assertThat(user).hasFieldOrPropertyWithValue("room", room);
    }

    @Test
    public void should_reject_saveAndFlush_for_duplicate_name_surname_combination() {
        String email = "aron@bolkhuis.ch";
        String name = "Aron";
        String surname = "Hoogeveen";
        User user1 = new User(email, name, surname, 101);
        User user2 = new User("different", name, surname, 202);
        User user3 = new User("email", name, "Different Surname", 303);

        assertDoesNotThrow(() -> repository.saveAndFlush(user1));
        assertDoesNotThrow(() -> repository.saveAndFlush(user3));
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(user2));
    }

    @Test
    public void should_reject_saveAndFlush_for_duplicate_email() {
        String email = "aron@bolkhuis.ch";
        User user1 = new User(email, "name1", "surname1", 1);
        User user2 = new User(email, "name2", "surname2", 2);

        repository.saveAndFlush(user1);
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(user2));
    }

    @Test
    public void should_reject_flush_for_duplicate_email() {
        String email = "aron@bolkhuis.ch";
        User user1 = new User(email, "name1", "surname1", 1);
        User user2 = new User(email, "name2", "surname2", 2);

        repository.saveAll(List.of(user1, user2));

        assertThrows(DataIntegrityViolationException.class, () -> repository.flush());
    }

    @Test
    public void should_reject_saveAllAndFlush_for_duplicate_email() {
        String email = "aron@bolkhuis.ch";
        User user1 = new User(email, "name1", "surname1", 1);
        User user2 = new User(email, "name2", "surname2", 2);

        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAllAndFlush(List.of(user1, user2)));
    }

    @Test
    public void should_reject_saveAndFlush_for_duplicate_room() {
        int room = 101;
        User user1 = new User("email 1", "name 1", "surname 1", room);
        User user2 = new User("email 2", "name 2", "surname 2", room);

        repository.saveAndFlush(user1);
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(user2));
    }

}
