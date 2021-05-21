package Application.Data.Repositories;

import Application.Entities.Course;
import Application.Entities.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {

    Optional<List<Lesson>> findAllByDate(LocalDate date);

    Optional<List<Lesson>> findAllByCourse(Course courseId);

    Optional<List<Lesson>> findAllByDateAndCourse(LocalDate date, Course course);
}
