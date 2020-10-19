package Application.Repositories;

import Application.Entities.Discipline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends CrudRepository<Discipline,Long> {


}
