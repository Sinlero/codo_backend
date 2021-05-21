package Application.Controllers;

import Application.Entities.Lesson;
import Application.Services.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
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

    @RequestMapping("/getAll")
    public List<Lesson> getAll() {
        return lessonService.getAll();
    }

    @RequestMapping("/getByDay")
    public ResponseEntity getByDay(@RequestParam String date) {
        return lessonService.getByDay(date);
    }

    @RequestMapping("/getByCourse")
    public ResponseEntity getByCourse(@RequestParam Long courseId) {
        return lessonService.getByCourse(courseId);
    }

    @RequestMapping("/getByDateAndCourse")
    public ResponseEntity getByDateAndCourse(@RequestParam String date, @RequestParam Long courseId) {
        return lessonService.getByDayAndCourse(date, courseId);
    }
}
