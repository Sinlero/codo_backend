package Application.Servicies;

import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.UserEntities.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAll() {
        return (List<Teacher>) teacherRepository.findAll();
    }

    public ResponseEntity<String> add(Teacher teacher) {
        teacherRepository.save(teacher);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
