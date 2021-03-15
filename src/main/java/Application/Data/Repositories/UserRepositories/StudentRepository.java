package Application.Data.Repositories.UserRepositories;

import Application.Entities.UserEntities.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {

    Optional<Student> findStudentByLogin(String login);

    List<Student> findAllByOrderByIdAsc();
}
