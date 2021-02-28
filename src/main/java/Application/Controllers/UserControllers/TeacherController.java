package Application.Controllers.UserControllers;

import Application.Entities.UserEntities.Teacher;
import Application.Servicies.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private TeacherService teacherService;

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
}
