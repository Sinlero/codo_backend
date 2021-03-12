package Application.Services.UserServices;

import Application.Data.Repositories.DisciplineRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.Discipline;
import Application.Entities.UserEntities.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;
    private DisciplineRepository disciplineRepository;

    public TeacherService(TeacherRepository teacherRepository, DisciplineRepository disciplineRepository) {
        this.teacherRepository = teacherRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public List<Teacher> getAll() {
        return (List<Teacher>) teacherRepository.findAll();
    }

    public ResponseEntity<String> add(Teacher teacher) {
        if (teacherRepository.findTeacherByLogin(teacher.getLogin()).isPresent()) {
            return new ResponseEntity<>("Teacher with this login already have", HttpStatus.CONFLICT);
        }
        teacherRepository.save(teacher);
        return new ResponseEntity<>("Teacher added", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, Teacher updatedTeacher) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (!teacher.isPresent()) {
            return teacherNotFound();
        }
        updatedTeacher.setId(id);
        updatedTeacher.setPassword(teacher.get().getPassword());
        teacherRepository.save(updatedTeacher);
        return new ResponseEntity<>("Teacher updated", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(!teacher.isPresent()) {
            return teacherNotFound();
        }
        teacherRepository.deleteById(id);
        return new ResponseEntity<>("Teacher deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> changePassword(Long id, String password) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if(!teacher.isPresent()) {
            return teacherNotFound();
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
            return teacherNotFound();
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
            return teacherNotFound();
        }
        List<Discipline> oldDisciplines = teacher.get().getDisciplines();
        List<Discipline> removableDisciplines = (List<Discipline>) disciplineRepository.findAllById(disciplines);
        oldDisciplines.removeAll(removableDisciplines);
        teacher.get().setDisciplines(oldDisciplines);
        teacherRepository.save(teacher.get());
        return new ResponseEntity<>("Disciplines deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> teacherNotFound() {
        return new ResponseEntity<>("Teacher with this id not found", HttpStatus.NOT_FOUND);
    }
}
