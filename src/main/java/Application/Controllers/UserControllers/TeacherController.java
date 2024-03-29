package Application.Controllers.UserControllers;

import Application.Data.DTO.DisciplinesUpdate;
import Application.Entities.UserEntities.Teacher;
import Application.Services.UserServices.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @RequestMapping("/getAll")
    public List<Teacher> getAll() {
        return teacherService.getAll();
    }

    @RequestMapping("/add")
    public ResponseEntity<String> add(@RequestBody Teacher teacher) {
        return teacherService.add(teacher);
    }

    @RequestMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Teacher teacher) {
        return teacherService.update(id, teacher);
    }

    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return teacherService.delete(id);
    }

    @RequestMapping("/{id}/changePassword")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String password) {
        return teacherService.changePassword(id, password);
    }

    @RequestMapping("/addDisciplines")
    public ResponseEntity<String> addDisciplines(@RequestBody DisciplinesUpdate disciplinesUpdate) {
        return teacherService.addDisciplines(disciplinesUpdate.getUserId(), disciplinesUpdate.getDisciplines());
    }

    @RequestMapping("/deleteDisciplines")
    public ResponseEntity<String> deleteDisciplines(@RequestBody DisciplinesUpdate disciplinesUpdate) {
        return teacherService.deleteDisciplines(disciplinesUpdate.getUserId(), disciplinesUpdate.getDisciplines());
    }
}
