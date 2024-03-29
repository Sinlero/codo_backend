package Application.Services.EventServices;

import Application.Data.Repositories.EventRepositories.EventRepository;
import Application.Data.Repositories.ImageRepository;
import Application.Entities.EventEntities.Event;
import Application.Entities.Image;
import Application.Services.FileService;
import Application.Utils.Response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class EventService {

    private final ImageRepository imageRepository;
    private final EventRepository eventRepository;
    private final FileService fileService;
    private final ResponseUtil responseUtil;

    public EventService(ImageRepository imageRepository,
                        EventRepository eventRepository,
                        FileService fileService,
                        ResponseUtil responseUtil) {
        this.imageRepository = imageRepository;
        this.eventRepository = eventRepository;
        this.fileService = fileService;
        this.responseUtil = responseUtil;
    }

    public ResponseEntity<String> upload(MultipartFile file, String head, String previewText, String fullText,
                                         String date) {
        Event event;
        File newFile = new File(String.valueOf(FileService.FILES_PATH));
        LocalDate localDate = LocalDate.parse(date);
        if (file == null || file.isEmpty()) {
            event = getEventWithDefaultImage(head, previewText, fullText, localDate);
        } else {
            newFile = FileService.saveImage(newFile, file);
            Image image = new Image(newFile.getAbsolutePath());
            imageRepository.save(image);
            event = new Event(head, previewText, fullText, image, localDate);
        }
        eventRepository.save(event);
        return new ResponseEntity<>(event.getId().toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent()) {
            return responseUtil.notFoundId("Event");
        }
        eventRepository.deleteById(id);
        Optional<Image> imageEntity = imageRepository.findById(event.get().getImage().getId());
        if (imageEntity.get().getId() != 1) {
            fileService.delete(imageEntity.get());
        }
        return new ResponseEntity<>("Event deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, String head, String previewText, String fullText) {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent()) {
            return responseUtil.notFoundId("Event");
        }
        event.get().setHead(head);
        event.get().setPreviewText(previewText);
        event.get().setFullText(fullText);
        eventRepository.save(event.get());
        return new ResponseEntity<>("Event updated", HttpStatus.OK);
    }

    private Event getEventWithDefaultImage(String head, String previewText, String fullText, LocalDate date) {
        Optional<Image> image = imageRepository.findById((long) 1);
        return new Event(head, previewText, fullText, image.get(), date);
    }

    public ResponseEntity getAll() {
        return ResponseEntity.ok(eventRepository.findAll());
    }

    public ResponseEntity getActual() {
        LocalDate date = LocalDate.now();
        return ResponseEntity.ok(eventRepository.findEventsByDateGreaterThanEqualOrderByDateAsc(date));
    }

    public ResponseEntity<Optional<Event>> getById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent()) {
            return null;
        }
        return ResponseEntity.ok(event);
    }

    public String getFullTextById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent()) {
            return responseUtil.notFoundId("Event").getBody();
        } else {
            return event.get().getFullText();
        }
    }
}
