package Application.Services;

import Application.Data.Repositories.CourseRepository;
import Application.Data.Repositories.DisciplineRepository;
import Application.Data.Repositories.LessonRepository;
import Application.Entities.Course;
import Application.Entities.Discipline;
import Application.Entities.Lesson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private LessonRepository lessonRepository;
    private DisciplineRepository disciplineRepository;
    private CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository, DisciplineRepository disciplineRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.disciplineRepository = disciplineRepository;
        this.courseRepository = courseRepository;
    }

    public ResponseEntity<String> add(Lesson lesson) {
        Optional<Discipline> discipline = disciplineRepository.findById(lesson.getDiscipline().getId());
        if (!discipline.isPresent()) {
            return new ResponseEntity<>("Discipline with this id not found", HttpStatus.NOT_FOUND);
        }
        lessonRepository.save(lesson);
        return new ResponseEntity<>("Lesson added", HttpStatus.OK);
    }

    public ResponseEntity<String> addHomework(Long id, String homework) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (!lesson.isPresent()) {
            return notFoundLesson();
        }
        lesson.get().setHomework(homework);
        lessonRepository.save(lesson.get());
        return new ResponseEntity<>("Homework added", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (!lesson.isPresent()) {
            return notFoundLesson();
        }
        lessonRepository.delete(lesson.get());
        return new ResponseEntity<>("Lesson deleted", HttpStatus.OK);
    }

    public List<Lesson> getAll() {
        return (List<Lesson>) lessonRepository.findAll();
    }

    public ResponseEntity<String> notFoundLesson() {
        return new ResponseEntity<>("Lesson with this id not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity getByDay(String date) {
        String[] dateArray = date.split("-");
        LocalDate lessonDate = LocalDate.of(Integer.valueOf(dateArray[0]),
                                            Integer.valueOf(dateArray[1]),
                                            Integer.valueOf(dateArray[2]));
        Optional<List<Lesson>> lessons = lessonRepository.findAllByDate(lessonDate);
        if (!lessons.isPresent()) {
            return new ResponseEntity("Lessons on this day not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lessons.get());
    }

    public ResponseEntity getByCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            return new ResponseEntity("Course not found", HttpStatus.NOT_FOUND);
        }
        Optional<List<Lesson>> lessons = lessonRepository.findAllByCourse(course.get());
        if (!lessons.isPresent()) {
            return new ResponseEntity("Lessons on this course not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lessons.get());
    }
}
