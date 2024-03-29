package Application.Services;

import Application.Data.Repositories.CourseRepository;
import Application.Entities.Course;
import Application.Utils.Response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final ResponseUtil responseUtil;

    public CourseService(CourseRepository courseRepository, ResponseUtil responseUtil) {
        this.courseRepository = courseRepository;
        this.responseUtil = responseUtil;
    }

    public ResponseEntity<String> add(String name) {
        courseRepository.save(new Course(name));
        return new ResponseEntity<>("Course added", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()) {
            return responseUtil.notFoundId("Course");
        }
        courseRepository.delete(course.get());
        return new ResponseEntity<>("Course deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, String name) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()) {
            return responseUtil.notFoundId("Course");
        }
        course.get().setName(name);
        courseRepository.save(course.get());
        return new ResponseEntity<>("Course updated", HttpStatus.OK);
    }

    public List<Course> getAll() {
        return courseRepository.findAllByOrderByIdAsc();
    }

}
