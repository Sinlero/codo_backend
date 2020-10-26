package Application.Servicies;

import Application.Entities.News;
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

    NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public ResponseEntity<String> upload(String head, String text, MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Error", HttpStatus.NO_CONTENT);
        }
        File newFile = new File(System.getProperty("user.dir") + "/NewsImages/");
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        newFile = new File(newFile, file.getOriginalFilename());
        try {
            FileOutputStream stream = new FileOutputStream(newFile);
            stream.write(file.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String date = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + " " +
                LocalDateTime.now().getDayOfMonth() + "." + LocalDateTime.now().getMonth().getValue() + "." +
                LocalDateTime.now().getYear();
        News news = new News(head, text, newFile.getAbsolutePath(), date);
        newsRepository.save(news);
        System.out.println(newFile);
        Optional<News> newsId = newsRepository.findByImg(newFile.getAbsolutePath());
        if (!newsId.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsId.get().getId().toString(), HttpStatus.OK);
    }

    public HttpEntity<byte[]> getImage(Long id) {
        Optional<News> news = newsRepository.findById(id);
        try {
            if (!news.isPresent()) {
                return new HttpEntity(HttpStatus.NOT_FOUND);
            }
            File image = new File(news.get().getImg());
            return new HttpEntity<>(getBytes(image));
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
}
