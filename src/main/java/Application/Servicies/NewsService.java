package Application.Servicies;

import Application.Entities.Image;
import Application.Entities.News;
import Application.Repositories.ImageRepository;
import Application.Repositories.NewsRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NewsService {

    private NewsRepository newsRepository;
    private ImageRepository imageRepository;

    NewsService(NewsRepository newsRepository, ImageRepository imageRepository) {
        this.newsRepository = newsRepository;
        this.imageRepository = imageRepository;
    }

    public ResponseEntity<String> upload(MultipartFile file, String head, String previewText, String fullText) {
        File newFile = new File(String.valueOf(FileService.FILES_PATH));
        String date = LocalDateTime.now().getDayOfMonth() + "." + LocalDateTime.now().getMonth().getValue() + "." +
                LocalDateTime.now().getYear();
        News news;
        if (file == null) {
            news = getNewsWithDefaultImage(head, previewText, fullText, date);
        } else if (file.isEmpty()) {
            news = getNewsWithDefaultImage(head, previewText, fullText, date);
        } else {
            newFile = FileService.saveImage(newFile, file);
            Image image = new Image(newFile.getAbsolutePath());
            imageRepository.save(image);
            news = new News(head, previewText, fullText, image, date);
        }
        newsRepository.save(news);
        return new ResponseEntity<>(news.getId().toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        newsRepository.deleteById(id);
        Optional<Image> imageEntity = imageRepository.findById(news.get().getImage().getId());
        if (imageEntity.get().getId() != 1) {
            File image = new File(imageEntity.get().getPath());
            image.delete();
            imageRepository.deleteById(imageEntity.get().getId());
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<String> updateNews(Long id, String head, String previewText, String fullText) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return new ResponseEntity<>("News with this id not found", HttpStatus.NOT_FOUND);
        }
        news.get().setHead(head);
        news.get().setPreviewText(previewText);
        news.get().setFullText(fullText);
        newsRepository.save(news.get());
        return new ResponseEntity<>("News updated", HttpStatus.OK);
    }

    public News getNewsWithDefaultImage(String head, String previewText, String fullText, String date) {
        Optional<Image> image = imageRepository.findById((long) 1);
        return new News(head, previewText, fullText, image.get(), date);
    }

    public HttpEntity<byte[]> getImage(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        try {
            if (!image.isPresent()) {
                return new HttpEntity(HttpStatus.NOT_FOUND);
            }
            File imageFile = new File(image.get().getPath());
            return new HttpEntity<>(FileService.getBytes(imageFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity getAll() {
        return ResponseEntity.ok(newsRepository.findAllByOrderByIdDesc());
    }

    public String getFullTextNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return "News with this id not found";
        } else {
            return news.get().getFullText();
        }
    }

}
