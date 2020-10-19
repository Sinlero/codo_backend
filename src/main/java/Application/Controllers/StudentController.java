package Application.Controllers;

import Application.Servicies.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/student/getDisciplinesById")
    public ResponseEntity getDisciplinesById(@RequestParam Long id) {
        return ResponseEntity.ok(studentService.getDisciplinesById(id));
    }
}
