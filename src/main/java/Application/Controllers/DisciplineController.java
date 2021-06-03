package Application.Controllers;

import Application.Services.DisciplineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/discipline")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_TEACHER', 'ROLE_ADMIN')")
    @RequestMapping("/getAll")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(disciplineService.getAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/add")
    public ResponseEntity<String> addDiscipline(@RequestParam String name, @RequestParam BigDecimal cost,
                                                @RequestParam(required = false) String colorCode) {
        return disciplineService.add(name, cost, colorCode);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return disciplineService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam String name,
                                         @RequestParam BigDecimal cost,
                                         @RequestParam String colorCode) {
        return disciplineService.update(id, name, cost, colorCode);
    }
}
