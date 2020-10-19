package Application.Controllers;

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

    @RequestMapping("/postDisciplines")
    @ResponseBody
    public String  changeDisciplines(@RequestBody DisciplinesUpdate update) {
        return studentService.changeDisciplines(update.getUserId(), update.getDisciplines());
    }
}
