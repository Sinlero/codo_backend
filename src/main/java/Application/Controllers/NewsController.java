package Application.Controllers;

import Application.Servicies.NewsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                         @RequestParam String text) {
        return newsService.upload(head, text, file);
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return newsService.deleteNewsById(id);
    }

    @RequestMapping("/update")
    public ResponseEntity<String> update(@RequestParam Long id, @RequestParam String head,
                                         @RequestParam String text) {
        return newsService.updateNews(id, head, text);
    }

    @RequestMapping("/getImage")
    public HttpEntity<byte[]> getImage(@RequestParam("id") Long id) {
        return newsService.getImage(id);
    }

    @RequestMapping("/getAll")
    public ResponseEntity getAllNews() {
        return newsService.getAll();
    }
}
