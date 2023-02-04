package ch.bolkhuis.declabo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseUserRepository<T extends User> extends JpaRepository<T, Long> {
    T findByEmail(String email);

    boolean existsByEmail(String email);

    List<T> findByName(String name);

    List<T> findBySurname(String surname);

    // there is a unique constraint on {name,surname}
    T findByNameAndSurname(String name, String surname);

    boolean existsByNameAndSurname(String name, String surname);

    T findByRoom(int room);

    boolean existsByRoom(int room);

    boolean existsByNameAndSurnameAndIdNot(String name, String surname, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByRoomAndIdNot(int room, Long id);
}
