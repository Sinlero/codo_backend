package Application.Controllers;

import Application.Entities.Course;
import Application.Services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/add")
    public ResponseEntity<String> add(@RequestParam String name) {
        return courseService.add(name);
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return courseService.delete(id);
    }

    @RequestMapping("/update")
    public ResponseEntity<String> update(@RequestBody Course course) {
        return courseService.update(course.getId(), course.getName());
    }

    @RequestMapping("/getAll")
    public List<Course> getAll() {
        return courseService.getAll();
    }
}
