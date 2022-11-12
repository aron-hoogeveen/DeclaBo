package ch.bolkhuis.declabo.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BaseUserRepository<T extends User> extends CrudRepository<T, Long> {
    List<T> findByName();
    List<T> findBySurname();
    T findByNameAndSurname(); // there is a unique constraint on {name,surname}
    T findByRoom();
}
