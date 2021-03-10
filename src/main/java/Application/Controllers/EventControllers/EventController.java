package Application.Controllers.EventControllers;

import Application.Services.EventServices.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam(required = false) MultipartFile file, @RequestParam String head,
                                         @RequestParam String previewText, @RequestParam String fullText,
                                         @RequestParam String date) {
        return eventService.upload(file, head, previewText, fullText, date);
    }

    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return eventService.delete(id);
    }

    @RequestMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam String head,
                                         @RequestParam String previewText,
                                         @RequestParam String fullText) {
        return eventService.update(id, head, previewText, fullText);
    }

    @RequestMapping("/getAll")
    public ResponseEntity getAll() {
        return eventService.getAll();
    }

    @RequestMapping("/getActual")
    public ResponseEntity getActual() {
        return eventService.getActual();
    }

    @RequestMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @RequestMapping("/{id}/fullText")
    public String getFullText(@PathVariable Long id){
        return eventService.getFullTextById(id);
    }
}
