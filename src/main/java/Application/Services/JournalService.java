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

    private LessonRepository lessonRepository;
    private StudentRepository studentRepository;
    private JournalRepository journalRepository;

    public JournalService(LessonRepository lessonRepository, StudentRepository studentRepository, JournalRepository journalRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
        this.journalRepository = journalRepository;
    }

    @Transactional
    public ResponseEntity<String> add(JournalUpdate journalUpdate) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(journalUpdate.getLessonId());
        if (!lessonOptional.isPresent()) {
            return ResponseUtil.notFoundId("Lesson");
        }
        List<Journal> journal = new ArrayList<>();
        for (StudentInfo studentInfo : journalUpdate.getJournalList()) {
            Optional<Student> studentOptional = studentRepository.findById(studentInfo.getStudentId());
            if (!studentOptional.isPresent()) {
                return ResponseUtil.notFoundId("Student");
            }
            journal.add(new Journal(studentOptional.get(), lessonOptional.get(), studentInfo.getPresence(), studentInfo.getMark()));
        }
        for (Journal saveJournal : journal) {
            BigDecimal balance = saveJournal.getStudent().getBalance();
            BigDecimal cost = lessonOptional.get().getDiscipline().getCost();
            saveJournal.getStudent().setBalance(balance.subtract(cost));
            journalRepository.save(saveJournal);
        }
        return new ResponseEntity<>("Journal added", HttpStatus.OK);
    }
}
