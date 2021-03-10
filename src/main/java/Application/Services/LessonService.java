package Application.Services;

import Application.Data.Repositories.DisciplineRepository;
import Application.Data.Repositories.LessonRepository;
import Application.Entities.Discipline;
import Application.Entities.Lesson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonService {

    private LessonRepository lessonRepository;
    private DisciplineRepository disciplineRepository;

    public LessonService(LessonRepository lessonRepository, DisciplineRepository disciplineRepository) {
        this.lessonRepository = lessonRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public ResponseEntity<String> add(Lesson lesson) {
        Optional<Discipline> discipline = disciplineRepository.findById(lesson.getDiscipline().getId());
        if (!discipline.isPresent()) {
            return new ResponseEntity<>("Discipline with this id not found", HttpStatus.NOT_FOUND);
        }
        lessonRepository.save(lesson);
        return new ResponseEntity<>("Lesson added", HttpStatus.OK);
    }
}
