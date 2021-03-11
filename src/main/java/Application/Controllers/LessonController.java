package Application.Controllers;

import Application.Entities.Lesson;
import Application.Services.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @RequestMapping("/add")
    public ResponseEntity<String> add(@RequestBody Lesson lesson) {
        return lessonService.add(lesson);
    }

    @RequestMapping("/{id}/addHomework")
    public ResponseEntity<String> addHomework(@PathVariable Long id, @RequestParam String homework) {
        return lessonService.addHomework(id, homework);
    }

    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return lessonService.delete(id);
    }

}
