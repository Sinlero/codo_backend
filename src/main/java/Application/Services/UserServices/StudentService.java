package Application.Services.UserServices;

import Application.Data.Repositories.DisciplineRepository;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Entities.Discipline;
import Application.Entities.UserEntities.Student;
import Application.Utils.Response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResponseUtil responseUtil;


    public StudentService(StudentRepository studentRepository,
                          DisciplineRepository disciplineRepository,
                          ResponseUtil responseUtil) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
        this.responseUtil = responseUtil;
    }

    public List<Discipline> getDisciplinesByStudentId(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return null;
        }
        return student.get().getDisciplines();
    }

    public ResponseEntity<String> addDisciplinesForStudent(Long userId, Set<Long> disciplines) {
        Student student = studentRepository.findById(userId).orElse(null);
        if (student == null) {
            return responseUtil.notFoundId("Student");
        }
        List<Discipline> oldDisciplines = student.getDisciplines();
        List<Discipline> newDisciplines = (List<Discipline>) disciplineRepository.findAllById(disciplines);
        newDisciplines.addAll(oldDisciplines);
        student.setDisciplines(newDisciplines);
        studentRepository.save(student);
        return new ResponseEntity<>("Disciplines added for student", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteDisciplinesOfStudent(Long userId, Set<Long> disciplines) {
        Student student = studentRepository.findById(userId).orElse(null);
        if (student == null) {
            return responseUtil.notFoundId("Student");
        }
        List<Discipline> oldDisciplines = student.getDisciplines();
        List<Discipline> disciplineList = (List<Discipline>) disciplineRepository.findAllById(disciplines);
        oldDisciplines.removeAll(disciplineList);
        student.setDisciplines(oldDisciplines);
        studentRepository.save(student);
        return new ResponseEntity<>("Disciplines deleted for student", HttpStatus.OK);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllByOrderByIdAsc();
    }

    public ResponseEntity<String> addStudent(Student newStudent) {
        if (responseUtil.userIsExist(newStudent)) {
            return new ResponseEntity<>("User with this login already exists", HttpStatus.CONFLICT);
        }
        newStudent.setPrivilege(3);
        studentRepository.save(newStudent);
        return new ResponseEntity<>("Student added", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return responseUtil.notFoundId("Student");
        }
        studentRepository.deleteById(id);
        return new ResponseEntity<>("Student deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, Student updatedStudent) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return responseUtil.notFoundId("Student");
        }
        updatedStudent.setId(student.get().getId());
        updatedStudent.setPassword(student.get().getPassword());
        studentRepository.save(updatedStudent);
        return new ResponseEntity<>("Student updated", HttpStatus.OK);
    }

    public ResponseEntity<String> changePassword(Long id, String password) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return responseUtil.notFoundId("Student");
        }
        if (password == null || password.trim().isEmpty()) {
            return new ResponseEntity<>("Password field is empty", HttpStatus.BAD_REQUEST);
        }
        student.get().setPassword(password);
        studentRepository.save(student.get());
        return new ResponseEntity<>("Password updated", HttpStatus.OK);
    }

    public List<Student> getAllByCourse(Long id_course) {
        return studentRepository.findAllByCourseId(id_course);
    }

}
