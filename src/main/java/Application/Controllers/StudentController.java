package Application.Controllers;

import Application.Entities.Student;
import Application.Models.DisciplinesUpdate;
import Application.Servicies.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/getDisciplinesById")
    public ResponseEntity getDisciplinesById(@RequestParam Long id) {
        return ResponseEntity.ok(studentService.getDisciplinesById(id));
    }

    @RequestMapping("/addDisciplines")
    @ResponseBody
    public String  addDisciplines(@RequestBody DisciplinesUpdate update) {
        return studentService.addDisciplines(update.getUserId(), update.getDisciplines());
    }

    @RequestMapping("/deleteDisciplines")
    @ResponseBody
    public String deleteDisciplines(@RequestBody DisciplinesUpdate update) {
        return studentService.deleteDisciplines(update.getUserId(), update.getDisciplines());
    }

    @RequestMapping("/getAllStudents")
    public ResponseEntity getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @RequestMapping("/addStudent")
    @ResponseBody
    public String addStudent(@RequestBody Student newStudent) {
        return studentService.addStudent(newStudent);
    }

    @RequestMapping("/deleteStudentById")
    public String deleteStudent(@RequestParam Long id) {
        return studentService.deleteStudent(id);
    }

}
