package Application.Data.Repositories;

import Application.Entities.Journal;
import Application.Entities.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalRepository extends CrudRepository<Journal, Long> {

    Optional<List<Journal>> findAllByLesson(Lesson lesson);
}
