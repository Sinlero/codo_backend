package Application.Controllers;

import Application.Servicies.DisciplineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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
                                                @RequestParam(required = false) String colorCode) {
        return disciplineService.addDiscipline(name, cost, colorCode);
    }

    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return disciplineService.delete(id);
    }

    @RequestMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam String name,
                                         @RequestParam BigDecimal cost,
                                         @RequestParam String colorCode) {
        return disciplineService.update(id, name, cost, colorCode);
    }
}
