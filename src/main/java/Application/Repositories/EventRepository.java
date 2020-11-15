package Application.Repositories;

import Application.Entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    // irrelevant events
    // List<Event> findEventsByDateLess(LocalDate date);

    // actual events
    List<Event> findEventsByDateGreaterThanEqualOrderByDateAsc(LocalDate date);
}
