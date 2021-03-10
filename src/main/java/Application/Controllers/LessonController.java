package Application.Controllers;

import Application.Entities.Lesson;
import Application.Services.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
