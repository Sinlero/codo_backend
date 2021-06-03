package Application.Services.EventServices;

import Application.Data.Repositories.EventRepositories.NewsRepository;
import Application.Data.Repositories.ImageRepository;
import Application.Entities.EventEntities.News;
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
public class NewsService {

    private final ImageRepository imageRepository;
    private final NewsRepository newsRepository;
    private final FileService fileService;
    private final ResponseUtil responseUtil;

    NewsService(ImageRepository imageRepository,
                NewsRepository newsRepository,
                FileService fileService,
                ResponseUtil responseUtil) {
        this.imageRepository = imageRepository;
        this.newsRepository = newsRepository;
        this.fileService = fileService;
        this.responseUtil = responseUtil;
    }

    public ResponseEntity<String> upload(MultipartFile file, String head, String previewText, String fullText) {
        File newFile = new File(String.valueOf(FileService.FILES_PATH));
        LocalDate date = LocalDate.now();
        News news;
        if (file == null || file.isEmpty()) {
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
            return responseUtil.notFoundId("News");
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
            return responseUtil.notFoundId("News");
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
            return responseUtil.notFoundId("News").getBody();
        } else {
            return news.get().getFullText();
        }
    }

}
