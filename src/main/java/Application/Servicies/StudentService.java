package Application.Servicies;

import Application.Data.Repositories.*;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Entities.Discipline;
import Application.Entities.UserEntities.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private DisciplineRepository disciplineRepository;


    public StudentService(StudentRepository studentRepository, DisciplineRepository disciplineRepository) {
        this.studentRepository = studentRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public List<Discipline> getDisciplinesByStudentId(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return null;
        }
        return student.get().getDisciplines();
    }

    public String addDisciplinesForStudent(Long userId, Set<Long> disciplines) {
        Student student = studentRepository.findById(userId).orElse(null);
        if (student == null) {
            return "Student not found";
        }
        List<Discipline> oldDisciplines = student.getDisciplines();
        Iterable<Discipline> disciplineList = disciplineRepository.findAllById(disciplines);
        oldDisciplines.addAll((Collection<Discipline>) disciplineList);
        student.setDisciplines(oldDisciplines);
        studentRepository.save(student);
        return "Success";
    }

    public String deleteDisciplinesOfStudent(Long userId, Set<Long> disciplines) {
        Student student = studentRepository.findById(userId).orElse(null);
        if (student == null) {
            return "Student not found";
        }
        List<Discipline> oldDisciplines = student.getDisciplines();
        Iterable<Discipline> disciplineList = disciplineRepository.findAllById(disciplines);
        oldDisciplines.removeAll((Collection<Discipline>) disciplineList);
        student.setDisciplines(oldDisciplines);
        studentRepository.save(student);
        return "Success";
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public String addStudent(Student newStudent) {
        studentRepository.save(newStudent);
        return "Success";
    }

    public String deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return "Student with this id not found";
        }
        studentRepository.deleteById(id);
        return "Success";
    }

    public ResponseEntity<String> update(Long id, Student updatedStudent) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return new ResponseEntity<>("Student with this id not found", HttpStatus.NOT_FOUND);
        }
        updatedStudent.setId(student.get().getId());
        updatedStudent.setPassword(student.get().getPassword());
        studentRepository.save(updatedStudent);
        return new ResponseEntity<>("Student updated", HttpStatus.OK);
    }

    public ResponseEntity<String> changePassword(Long id, String password) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return new ResponseEntity<>("Student with this id not found", HttpStatus.NOT_FOUND);
        }
        if (password == null || password.trim().isEmpty()) {
            return new ResponseEntity<>("Password field is empty", HttpStatus.BAD_REQUEST);
        }
        student.get().setPassword(password);
        studentRepository.save(student.get());
        return new ResponseEntity<>("Password updated", HttpStatus.OK);
    }

}
