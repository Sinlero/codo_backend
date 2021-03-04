package Application.Controllers.UserControllers;

import Application.Entities.UserEntities.Student;
import Application.Data.DisciplinesUpdate;
import Application.Servicies.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/addDisciplines")
    public ResponseEntity<String> addDisciplines(@RequestBody DisciplinesUpdate update) {
        return studentService.addDisciplinesForStudent(update.getUserId(), update.getDisciplines());
    }

    @RequestMapping("/deleteDisciplines")
    public ResponseEntity<String> deleteDisciplines(@RequestBody DisciplinesUpdate update) {
        return studentService.deleteDisciplinesOfStudent(update.getUserId(), update.getDisciplines());
    }

    @RequestMapping("/getAll")
    public ResponseEntity getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @RequestMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody Student newStudent) {
        return studentService.addStudent(newStudent);
    }

    @RequestMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @RequestMapping("/{id}/changePassword")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String password) {
        return studentService.changePassword(id, password);
    }
}
