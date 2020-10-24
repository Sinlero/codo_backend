package Application.Servicies;

import Application.Entities.Discipline;
import Application.Entities.Student;
import Application.Repositories.DisciplineRepository;
import Application.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    DisciplineRepository disciplineRepository;

    public Set<Discipline> getDisciplinesByStudentId(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            return null;
        }
        return student.get().getDisciplines();
    }

    public String addDisciplinesForStudent(Long userId, Set<Long> disciplines) {
        Student student = studentRepository.findById(userId).orElse(null);
        if(student == null) {
            return "Student not found";
        }
        Set<Discipline> oldDisciplines = student.getDisciplines();
        Iterable<Discipline> disciplineList = disciplineRepository.findAllById(disciplines);
        oldDisciplines.addAll((Collection<Discipline>) disciplineList);
        student.setDisciplines(oldDisciplines);
        studentRepository.save(student);
        return "Success";
    }

    public String deleteDisciplinesOfStudent(Long userId, Set<Long> disciplines) {
        Student student = studentRepository.findById(userId).orElse(null);
        if(student == null) {
            return "Student not found";
        }
        Set<Discipline> oldDisciplines = student.getDisciplines();
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
        if(!student.isPresent()) {
            return null;
        }
        studentRepository.deleteById(id);
        return "Success";
    }
}
