package Application.Controllers.UserControllers;

import Application.Entities.UserEntities.Student;
import Application.Data.DTO.DisciplinesUpdate;
import Application.Services.UserServices.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/{id}/getDisciplines")
    public ResponseEntity getDisciplinesById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getDisciplinesByStudentId(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping("/addDisciplines")
    public ResponseEntity<String> addDisciplines(@RequestBody DisciplinesUpdate update) {
        return studentService.addDisciplinesForStudent(update.getUserId(), update.getDisciplines());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping("/deleteDisciplines")
    public ResponseEntity<String> deleteDisciplines(@RequestBody DisciplinesUpdate update) {
        return studentService.deleteDisciplinesOfStudent(update.getUserId(), update.getDisciplines());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping("/getAll")
    public ResponseEntity getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody Student newStudent) {
        return studentService.addStudent(newStudent);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/{id}/changePassword")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String password) {
        return studentService.changePassword(id, password);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @RequestMapping("/getAllByCourse/{id_course}")
    public List<Student> getAllByCourse(@PathVariable Long id_course) {
        return studentService.getAllByCourse(id_course);
    }
}
