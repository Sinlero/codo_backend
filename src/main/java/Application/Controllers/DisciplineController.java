package Application.Controllers;

import Application.Servicies.DisciplineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;

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

    @RequestMapping("/add")
    public ResponseEntity<String> addDiscipline(@RequestParam String name, @RequestParam BigDecimal cost,
                                        @RequestParam ArrayList<Long> teacherIDs, @RequestParam String colorCode) {
        return disciplineService.addDiscipline(name, cost, teacherIDs, colorCode);
    }
}
