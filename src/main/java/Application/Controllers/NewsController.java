package Application.Controllers;

import Application.Entities.News;
import Application.Servicies.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam(required = false) MultipartFile file, @RequestParam String head,
                                         @RequestParam String previewText, @RequestParam String fullText) {
        return newsService.upload(file, head, previewText, fullText);
    }

    @RequestMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return newsService.delete(id);
    }

    @RequestMapping("/update")
    public ResponseEntity<String> update(@RequestParam Long id, @RequestParam String head,
                                         @RequestParam String previewText,
                                         @RequestParam String fullText) {
        return newsService.update(id, head, previewText, fullText);
    }

    @RequestMapping("/getAll")
    public ResponseEntity getAll() {
        return newsService.getAll();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Optional<News>> getById(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @RequestMapping("/{id}/fullText")
    public String getFullText(@PathVariable Long id) {
        return newsService.getFullText(id);
    }
}
