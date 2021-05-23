package Application.Controllers;

import Application.Data.DTO.Journal.JournalUpdate;
import Application.Data.DTO.Journal.StudentInfo;
import Application.Services.JournalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    private JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @RequestMapping("/add")
    public ResponseEntity<String> add(@RequestBody JournalUpdate journalUpdate) {
        return journalService.add(journalUpdate);
    }
}
