package Application.Controllers.EventControllers;

import Application.Entities.EventEntities.News;
import Application.Services.EventServices.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam(required = false) MultipartFile file, @RequestParam String head,
                                         @RequestParam String previewText, @RequestParam String fullText) {
        return newsService.upload(file, head, previewText, fullText);
    }

    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return newsService.delete(id);
    }

    @RequestMapping("/{id}/update")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam String head,
                                         @RequestParam String previewText,
                                         @RequestParam String fullText) {
        return newsService.update(id, head, previewText, fullText);
    }

    @RequestMapping("/public/getAll")
    public ResponseEntity getAll() {
        return newsService.getAll();
    }

    @RequestMapping("/public/{id}")
    public ResponseEntity<Optional<News>> getById(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @RequestMapping("/public/{id}/fullText")
    public String getFullText(@PathVariable Long id) {
        return newsService.getFullText(id);
    }
}
