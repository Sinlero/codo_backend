package Application.Utils.Response;

import Application.Data.Repositories.UserRepositories.AdministratorRepository;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.UserEntities.AbstractUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

    public boolean userIsUnique(AbstractUser user) {
        if (studentRepository.findStudentByLogin(user.getLogin()).isPresent()) {
            return false;
        }
        if (teacherRepository.findTeacherByLogin(user.getLogin()).isPresent()) {
            return false;
        }
        if (administratorRepository.findAdministratorByLogin(user.getLogin()).isPresent()) {
            return false;
        }
        return true;
    }

    public ResponseEntity<String> notFoundId(String entity) {
        return new ResponseEntity<>(String.format("%s with this id not found", entity), HttpStatus.NOT_FOUND);
    }
}
