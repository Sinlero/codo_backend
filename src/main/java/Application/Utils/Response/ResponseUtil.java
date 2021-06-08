package Application.Utils.Response;

import Application.Data.Repositories.UserRepositories.AdministratorRepository;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.UserEntities.AbstractUser;
import Application.Entities.UserEntities.Administrator;
import Application.Entities.UserEntities.Student;
import Application.Entities.UserEntities.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResponseUtil {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdministratorRepository administratorRepository;

    public ResponseUtil(StudentRepository studentRepository, TeacherRepository teacherRepository, AdministratorRepository administratorRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.administratorRepository = administratorRepository;
    }

    public boolean userIsExist(AbstractUser user) {
        Optional<Administrator> admin = administratorRepository.findAdministratorByLogin(user.getLogin());
        Optional<Teacher> teacher = teacherRepository.findTeacherByLogin(user.getLogin());
        Optional<Student> student = studentRepository.findStudentByLogin(user.getLogin());
        return admin.isPresent() || teacher.isPresent() || student.isPresent();
    }

    public ResponseEntity<String> notFoundId(String entity) {
        return new ResponseEntity<>(String.format("%s with this id not found", entity), HttpStatus.NOT_FOUND);
    }
}
