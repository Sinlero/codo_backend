package Application.Services;

import Application.Data.DTO.Journal.JournalUpdate;
import Application.Data.DTO.Journal.StudentInfo;
import Application.Data.Repositories.JournalRepository;
import Application.Data.Repositories.LessonRepository;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Entities.Journal;
import Application.Entities.Lesson;
import Application.Entities.UserEntities.Student;
import Application.Utils.Response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final JournalRepository journalRepository;
    private final ResponseUtil responseUtil;

    public JournalService(LessonRepository lessonRepository,
                          StudentRepository studentRepository,
                          JournalRepository journalRepository,
                          ResponseUtil responseUtil) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
        this.journalRepository = journalRepository;
        this.responseUtil = responseUtil;
    }

    @Transactional
    public ResponseEntity<String> add(JournalUpdate journalUpdate) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(journalUpdate.getLessonId());
        if (!lessonOptional.isPresent()) {
            return responseUtil.notFoundId("Lesson");
        }
        List<Journal> journal = new ArrayList<>();
        for (StudentInfo studentInfo : journalUpdate.getJournalList()) {
            Optional<Student> studentOptional = studentRepository.findById(studentInfo.getStudentId());
            if (!studentOptional.isPresent()) {
                return responseUtil.notFoundId("Student");
            }
            journal.add(new Journal(studentOptional.get(), lessonOptional.get(), studentInfo.getPresence(), studentInfo.getMark()));
        }
        for (Journal studentInfo : journal) {
            if(studentInfo.getPresence()) {
                BigDecimal balance = studentInfo.getStudent().getBalance();
                BigDecimal cost = lessonOptional.get().getDiscipline().getCost();
                studentInfo.getStudent().setBalance(balance.subtract(cost));
            }
            if(studentInfo.getStudent().getBalance().compareTo(new BigDecimal("0")) == -1) {
                studentInfo.getStudent().setDebtor(Boolean.TRUE);
            }
            journalRepository.save(studentInfo);
        }
        return new ResponseEntity<>("Journal added", HttpStatus.OK);
    }

    public ResponseEntity getAll() {
        return ResponseEntity.ok(journalRepository.findAll());
    }

    public ResponseEntity getByLesson(Long lessonId) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            return responseUtil.notFoundId("Lesson");
        }
        Optional<List<Journal>> journals = journalRepository.findAllByLesson(lessonOptional.get());
        if (!journals.isPresent()) {
            return new ResponseEntity("Journal with this lesson not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(journals.get());
    }
}
