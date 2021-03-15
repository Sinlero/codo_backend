package Application.Services;

import Application.Data.Repositories.CourseRepository;
import Application.Entities.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public ResponseEntity<String> add(String name) {
        courseRepository.save(new Course(name));
        return new ResponseEntity<>("Course added", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()) {
            return courseNotFound();
        }
        courseRepository.delete(course.get());
        return new ResponseEntity<>("Course deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, String name) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()) {
            return courseNotFound();
        }
        course.get().setName(name);
        courseRepository.save(course.get());
        return new ResponseEntity<>("Course updated", HttpStatus.OK);
    }

    public List<Course> getAll() {
        return courseRepository.findAllByOrderByIdAsc();
    }

    public ResponseEntity<String> courseNotFound() {
        return new ResponseEntity<>("Course with this id not found", HttpStatus.NOT_FOUND);
    }
}
