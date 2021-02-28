package Application.Servicies;

import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.UserEntities.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return new ResponseEntity<>("Teacher added", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(!teacher.isPresent()) {
            return new ResponseEntity<>("Teacher with this id not found", HttpStatus.NOT_FOUND);
        }
        teacherRepository.deleteById(id);
        return new ResponseEntity<>("Teacher deleted", HttpStatus.OK);
    }
}
