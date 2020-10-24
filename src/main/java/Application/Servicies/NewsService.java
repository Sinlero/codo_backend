package Application.Servicies;

import Application.Repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class NewsService {

    NewsRepository newsRepository;

    NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public ResponseEntity<String> upload(MultipartFile file) {
        if ((file == null) || (file.getOriginalFilename().equals(""))) {
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
        return new ResponseEntity<>(newFile.getAbsolutePath(), HttpStatus.OK);
    }
}
