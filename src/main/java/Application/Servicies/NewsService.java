package Application.Servicies;

import Application.Entities.Image;
import Application.Entities.News;
import Application.Repositories.ImageRepository;
import Application.Repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class NewsService {

    private ImageRepository imageRepository;
    private NewsRepository newsRepository;
    private FileService fileService;

    NewsService(ImageRepository imageRepository, NewsRepository newsRepository, FileService fileService) {
        this.imageRepository = imageRepository;
        this.newsRepository = newsRepository;
        this.fileService = fileService;
    }

    public ResponseEntity<String> upload(MultipartFile file, String head, String previewText, String fullText) {
        File newFile = new File(String.valueOf(FileService.FILES_PATH));
        LocalDate date = LocalDate.now();
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

    public ResponseEntity<String> delete(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        newsRepository.deleteById(id);
        Optional<Image> imageEntity = imageRepository.findById(news.get().getImage().getId());
        if (imageEntity.get().getId() != 1) {
            fileService.delete(imageEntity.get());
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, String head, String previewText, String fullText) {
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

    private News getNewsWithDefaultImage(String head, String previewText, String fullText, LocalDate date) {
        Optional<Image> image = imageRepository.findById((long) 1);
        return new News(head, previewText, fullText, image.get(), date);
    }

    public ResponseEntity getAll() {
        return ResponseEntity.ok(newsRepository.findAllByOrderByIdDesc());
    }

    public ResponseEntity<Optional<News>> getById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return null;
        }
        return ResponseEntity.ok(news);
    }

    public String getFullText(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return "News with this id not found";
        } else {
            return news.get().getFullText();
        }
    }

}
