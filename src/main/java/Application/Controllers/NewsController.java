package Application.Controllers;

import Application.Servicies.NewsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile file, @RequestParam String head,
                                         @RequestParam String text) {
        return newsService.upload(head, text, file);
    }

    @GetMapping("/getImage")
    public HttpEntity<byte[]> getImage(@RequestParam("id") Long id) throws IOException {
        return newsService.getImage(id);
    }
}
