package ch.bolkhuis.declabo.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void should_set_all_fields_on_construction() {
        String email = "aron@bolkhuis.ch";
        String name = "Aron";
        String surname = "Hoogeveen";
        int room = 101;
        User user = new User(email, name, surname, room);

        assertThat(user).hasFieldOrPropertyWithValue("email", email);
        assertThat(user).hasFieldOrPropertyWithValue("name", name);
        assertThat(user).hasFieldOrPropertyWithValue("surname", surname);
        assertThat(user).hasFieldOrPropertyWithValue("room", room);
    }

    @Test
    public void should_equal_when_name_and_surname_are_the_same() {
        String name = "Aron";
        String surname = "Hoogeveen";
        User u1 = new User("1234", name, surname, 1);
        User u2 = new User("5678", name, surname, 222);
        assertThat(u1).isEqualTo(u2);

        User u3 = new User("1234", "Gerrit", surname, 1);
        assertThat(u1).isNotEqualTo(u3);

        User u4 = new User("1234", name, "van Oort", 1);
        assertThat(u1).isNotEqualTo(u4);
    }

}
