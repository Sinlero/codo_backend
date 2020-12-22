package Application.Data.Repositories;

import Application.Entities.Journal;
import org.springframework.data.repository.CrudRepository;

public interface JournalRepository extends CrudRepository<Journal,Long> {

}
