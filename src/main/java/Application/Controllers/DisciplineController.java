package Application.Controllers;

import Application.Servicies.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {

    @Autowired
    DisciplineService disciplineService;

    @RequestMapping("/getAllDisciplines")
    public ResponseEntity getAllDisciplines() {
        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }
}
