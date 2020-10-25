package Application.Servicies;

import Application.Entities.News;
import Application.Repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class NewsService {

    NewsRepository newsRepository;

    NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public ResponseEntity<String> upload(String head, String text, MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Error", HttpStatus.NO_CONTENT);
        }
        File newFile = new File("/" + file.getOriginalFilename());
        try {
            FileOutputStream stream = new FileOutputStream(newFile);
            stream.write(file.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String date = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + " " +
                LocalDateTime.now().getDayOfMonth()+ "." + LocalDateTime.now().getMonth().getValue()+ "." +
                LocalDateTime.now().getYear();
        News news = new News(head, text, newFile.getAbsolutePath(), date);
        newsRepository.save(news);
        return new ResponseEntity<>(newFile.getAbsolutePath(), HttpStatus.OK);
    }
}
