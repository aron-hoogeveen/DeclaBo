package ch.bolkhuis.declabo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseUserRepository<T extends User> extends JpaRepository<T, Long> {
    List<T> findByName(String name);

    List<T> findBySurname(String surname);

    T findByNameAndSurname(String name, String surname); // there is a unique constraint on {name,surname}

    T findByRoom(int room);
}
