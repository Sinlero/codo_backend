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
                                         @RequestParam String previewText,
                                         @RequestParam(required = false) String fullText) {
        return newsService.upload(file, head, previewText, fullText);
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return newsService.deleteNewsById(id);
    }

    @RequestMapping("/update")
    public ResponseEntity<String> update(@RequestParam Long id, @RequestParam String head,
                                         @RequestParam String previewText,
                                         @RequestParam(required = false) String fullText) {
        return newsService.updateNews(id, head, previewText, fullText);
    }

    @RequestMapping("/getImage")
    public HttpEntity<byte[]> getImage(@RequestParam("id") Long id) {
        return newsService.getImage(id);
    }

    @RequestMapping("/getAll")
    public ResponseEntity getAllNews() {
        return newsService.getAll();
    }

    @RequestMapping("/getById")
    public String getNewsById(@RequestParam Long id) {
        return newsService.getFullTextNewsById(id);
    }

    @RequestMapping("/getFullTextById")
    public String getFullTextById(@RequestParam Long id) {
        return newsService.getFullTextNewsById(id);
    }
}
