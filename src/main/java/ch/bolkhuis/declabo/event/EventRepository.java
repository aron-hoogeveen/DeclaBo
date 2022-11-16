package ch.bolkhuis.declabo.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT DISTINCT e FROM Event e JOIN FETCH e.attendants")
    List<Event> findAllFetchAttendants();

    @Query("SELECT DISTINCT e FROM Event e JOIN FETCH e.funds")
    List<Event> findAllFetchFunds();

    @Query("SELECT DISTINCT e FROM Event e JOIN FETCH e.attendants JOIN FETCH e.funds")
    List<Event> findAllFetchAttendantsAndFunds();
}
