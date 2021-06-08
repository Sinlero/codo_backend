package Application.Services.UserServices;

import Application.Data.Repositories.DisciplineRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.Discipline;
import Application.Entities.UserEntities.Teacher;
import Application.Utils.Response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResponseUtil responseUtil;

    public TeacherService(TeacherRepository teacherRepository,
                          DisciplineRepository disciplineRepository,
                          ResponseUtil responseUtil) {
        this.teacherRepository = teacherRepository;
        this.disciplineRepository = disciplineRepository;
        this.responseUtil = responseUtil;
    }

    public List<Teacher> getAll() {
        return (List<Teacher>) teacherRepository.findAll();
    }

    public ResponseEntity<String> add(Teacher teacher) {
        if (responseUtil.userIsExist(teacher)) {
            return new ResponseEntity<>("User with this login already exists", HttpStatus.CONFLICT);
        }
        teacher.setPrivilege(2);
        teacherRepository.save(teacher);
        return new ResponseEntity<>("Teacher added", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, Teacher updatedTeacher) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (!teacher.isPresent()) {
            return responseUtil.notFoundId("Teacher");
        }
        updatedTeacher.setId(id);
        updatedTeacher.setPassword(teacher.get().getPassword());
        teacherRepository.save(updatedTeacher);
        return new ResponseEntity<>("Teacher updated", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(!teacher.isPresent()) {
            return responseUtil.notFoundId("Teacher");
        }
        teacherRepository.deleteById(id);
        return new ResponseEntity<>("Teacher deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> changePassword(Long id, String password) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(!teacher.isPresent()) {
            return responseUtil.notFoundId("Teacher");
        }
        if (password == null || password.trim().isEmpty()) {
            return new ResponseEntity<>("Password is empty", HttpStatus.BAD_REQUEST);
        }
        teacher.get().setPassword(password);
        teacherRepository.save(teacher.get());
        return new ResponseEntity<>("Password updated", HttpStatus.OK);
    }

    public ResponseEntity<String> addDisciplines(Long id, Set<Long> disciplines) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(!teacher.isPresent()) {
            return responseUtil.notFoundId("Teacher");
        }
        List<Discipline> oldDisciplines = teacher.get().getDisciplines();
        List<Discipline> newDisciplines = (List<Discipline>) disciplineRepository.findAllById(disciplines);
        newDisciplines.addAll(oldDisciplines);
        teacher.get().setDisciplines(newDisciplines);
        teacherRepository.save(teacher.get());
        return new ResponseEntity<>("Disciplines added", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteDisciplines(Long id, Set<Long> disciplines) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (!teacher.isPresent()) {
            return responseUtil.notFoundId("Teacher");
        }
        List<Discipline> oldDisciplines = teacher.get().getDisciplines();
        List<Discipline> removableDisciplines = (List<Discipline>) disciplineRepository.findAllById(disciplines);
        oldDisciplines.removeAll(removableDisciplines);
        teacher.get().setDisciplines(oldDisciplines);
        teacherRepository.save(teacher.get());
        return new ResponseEntity<>("Disciplines deleted", HttpStatus.OK);
    }

}
