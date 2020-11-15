package Application.Controllers;

import Application.Servicies.DisciplineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {

    DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @RequestMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(disciplineService.getAll());
    }
}
