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

    NewsRepository newsRepository;
    ImageRepository imageRepository;

    NewsService(NewsRepository newsRepository, ImageRepository imageRepository) {
        this.newsRepository = newsRepository;
        this.imageRepository = imageRepository;
    }

    public ResponseEntity<String> upload(String head, String text, MultipartFile file) {
        File newFile = new File(System.getProperty("user.dir") + "/NewsImages/");
        String date = LocalDateTime.now().getDayOfMonth() + "." + LocalDateTime.now().getMonth().getValue() + "." +
                LocalDateTime.now().getYear();
        News news;

        if (!newFile.exists()) {
            newFile.mkdirs();
        }

        if (file == null) {
            news = getNewsWithDefaultImage(head, text, date);
        } else if (file.isEmpty()) {
            news = getNewsWithDefaultImage(head, text, date);
        } else {
            newFile = new File(newFile, file.getOriginalFilename());

            try {
                FileOutputStream stream = new FileOutputStream(newFile);
                stream.write(file.getBytes());
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Image image = new Image(newFile.getAbsolutePath());
            imageRepository.save(image);
            news = new News(head, text, image, date);
        }
        newsRepository.save(news);
        return new ResponseEntity<>(news.getId().toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return new ResponseEntity<>("News not found", HttpStatus.NOT_FOUND);
        }
        Optional<Image> imageEntity = imageRepository.findById(news.get().getImage().getId());
        if (imageEntity.get().getId() != 1) {
            File image = new File(imageEntity.get().getPath());
            image.delete();
        }
        newsRepository.deleteById(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<String> updateNews(Long id, String head, String text) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return new ResponseEntity<>("News with this id not found", HttpStatus.NOT_FOUND);
        }
        news.get().setHead(head);
        news.get().setText(text);
        newsRepository.save(news.get());
        return new ResponseEntity<>("News updated", HttpStatus.OK);
    }

    public News getNewsWithDefaultImage(String head, String text, String date) {
        Optional<Image> image = imageRepository.findById((long) 1);
        News DefaultNews = new News(head, text, image.get(), date);
        return DefaultNews;
    }

    public HttpEntity<byte[]> getImage(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        try {
            if (!image.isPresent()) {
                return new HttpEntity(HttpStatus.NOT_FOUND);
            }
            File imageFile = new File(image.get().getPath());
            return new HttpEntity<>(getBytes(imageFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getBytes(File file) throws IOException {
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is too large");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        return bytes;
    }

    public ResponseEntity getAll() {
        return ResponseEntity.ok(newsRepository.findAllByOrderByIdDesc());
    }
}
