package Application.Data.Repositories.EventRepositories;

import Application.Entities.EventEntities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findEventsByDateGreaterThanEqualOrderByDateAsc(LocalDate date);

    @Override
    List<Event> findAll();
}
