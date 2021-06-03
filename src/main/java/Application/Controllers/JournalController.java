package Application.Controllers;

import Application.Data.DTO.Journal.JournalUpdate;
import Application.Services.JournalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @RequestMapping("/add")
    public ResponseEntity<String> add(@RequestBody JournalUpdate journalUpdate) {
        return journalService.add(journalUpdate);
    }

    @RequestMapping("/getAll")
    public ResponseEntity getAll() {
        return journalService.getAll();
    }

    @RequestMapping("/getByLesson")
    public ResponseEntity getByLesson(@RequestParam Long lessonId) {
        return journalService.getByLesson(lessonId);
    }
}
