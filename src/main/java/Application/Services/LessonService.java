package Application.Services;

import Application.Data.Repositories.CourseRepository;
import Application.Data.Repositories.DisciplineRepository;
import Application.Data.Repositories.LessonRepository;
import Application.Entities.Course;
import Application.Entities.Discipline;
import Application.Entities.Lesson;
import Application.Utils.Response.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LessonService {

    private final LessonRepository lessonRepository;
    private final DisciplineRepository disciplineRepository;
    private final CourseRepository courseRepository;
    private final ResponseUtil responseUtil;

    public LessonService(LessonRepository lessonRepository,
                         DisciplineRepository disciplineRepository,
                         CourseRepository courseRepository,
                         ResponseUtil responseUtil) {
        this.lessonRepository = lessonRepository;
        this.disciplineRepository = disciplineRepository;
        this.courseRepository = courseRepository;
        this.responseUtil = responseUtil;
    }

    public ResponseEntity<String> add(Lesson lesson) {
        Optional<Discipline> discipline = disciplineRepository.findById(lesson.getDiscipline().getId());
        if (!discipline.isPresent()) {
            return responseUtil.notFoundId("Discipline");
        }
        lessonRepository.save(lesson);
        return new ResponseEntity<>("Lesson added", HttpStatus.OK);
    }

    public ResponseEntity<String> addHomework(Long id, String homework) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (!lesson.isPresent()) {
            return responseUtil.notFoundId("Lesson");
        }
        lesson.get().setHomework(homework);
        lessonRepository.save(lesson.get());
        return new ResponseEntity<>("Homework added", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if (!lesson.isPresent()) {
            return responseUtil.notFoundId("Lesson");
        }
        lessonRepository.delete(lesson.get());
        return new ResponseEntity<>("Lesson deleted", HttpStatus.OK);
    }

    public List<Lesson> getAll() {
        return (List<Lesson>) lessonRepository.findAll();
    }

    public ResponseEntity getByDay(String date) {
        String[] dateArray = date.split("-");
        LocalDate lessonDate = LocalDate.of(Integer.parseInt(dateArray[0]),
                                            Integer.parseInt(dateArray[1]),
                                            Integer.parseInt(dateArray[2]));
        Optional<List<Lesson>> lessons = lessonRepository.findAllByDate(lessonDate);
        if (!lessons.isPresent()) {
            return new ResponseEntity("Lessons on this day not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lessons.get());
    }

    public ResponseEntity getByCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            return responseUtil.notFoundId("Course");
        }
        Optional<List<Lesson>> lessons = lessonRepository.findAllByCourse(course.get());
        if (!lessons.isPresent()) {
            return new ResponseEntity("Lessons on this course not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lessons.get());
    }

    public ResponseEntity getByDayAndCourse(String date, Long courseId) {
        String[] dateArray = date.split("-");
        LocalDate lessonDate = LocalDate.of(Integer.parseInt(dateArray[0]),
                                            Integer.parseInt(dateArray[1]),
                                            Integer.parseInt(dateArray[2]));
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            return responseUtil.notFoundId("Course");
        }
        Optional<List<Lesson>> optionalLessons = lessonRepository.findAllByDateAndCourse(lessonDate, course.get());
        if (!optionalLessons.isPresent()) {
            return new ResponseEntity("No lessons found for this course on this day", HttpStatus.NOT_FOUND);
        }
        List<Lesson> lessons = optionalLessons.get();
        List<Integer> numbersLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            numbersLessons.add(lesson.getLessonNumber());
        }
        Integer maxLesson = lessons.stream().max(Comparator.comparingInt(Lesson::getLessonNumber)).get().getLessonNumber();
        for (int i = 1; i <= maxLesson; i++) {
            if(!numbersLessons.contains(i)) {
                lessons.add(new Lesson(null,null, null, course.get(), i, null, null, lessonDate));
            }
        }
        lessons.sort(Comparator.comparingInt(Lesson::getLessonNumber));
        return ResponseEntity.ok(lessons);
    }
}
