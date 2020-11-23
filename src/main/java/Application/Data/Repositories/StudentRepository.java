package Application.Data.Repositories;

import Application.Entities.UserEntities.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {

    Optional<Student> findStudentByLogin(String login);
}
