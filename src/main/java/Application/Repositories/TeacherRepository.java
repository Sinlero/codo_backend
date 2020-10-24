package Application.Repositories;

import Application.Entities.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    Optional<Teacher> findTeacherByLogin(String login);
}
