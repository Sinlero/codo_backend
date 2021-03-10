package Application.Services;

import Application.Entities.EventEntities.Event;
import Application.Entities.Image;
import Application.Entities.EventEntities.News;
import Application.Data.Repositories.EventRepositories.EventRepository;
import Application.Data.Repositories.ImageRepository;
import Application.Data.Repositories.EventRepositories.NewsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {

    public static final File FILES_PATH = new File(System.getProperty("user.dir") + "/Images/");

    private NewsRepository newsRepository;
    private ImageRepository imageRepository;
    private EventRepository eventRepository;

    public FileService(NewsRepository newsRepository, ImageRepository imageRepository, EventRepository eventRepository) {
        this.newsRepository = newsRepository;
        this.imageRepository = imageRepository;
        this.eventRepository = eventRepository;
    }

    public static boolean init() {
        File newFile = new File(String.valueOf(FILES_PATH));
        if (!newFile.exists()) {
            return newFile.mkdirs();
        }
        return false;
    }

    public static File saveImage(File newFile, MultipartFile file) {
        newFile = new File(newFile, file.getOriginalFilename());
        if (newFile.exists()) {
            String extension = newFile.getName().substring(newFile.getName().lastIndexOf("."));
            String nameOfFile = UUID.randomUUID().toString().concat(extension);
            newFile = new File((newFile.getParent().concat(File.separator)).concat(nameOfFile));
        }
        try {
            FileOutputStream stream = new FileOutputStream(newFile);
            stream.write(file.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public static byte[] getBytes(File file) throws IOException {
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

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleaningPictures() {
        System.out.println("Cleaning images");
        TreeSet<Long> allId = new TreeSet<>();
        List<News> newses = newsRepository.findAll();
        List<Event> events = eventRepository.findAll();
        for (News news : newses) {
            allId.add(news.getImage().getId());
            allId.addAll(getImagesId(news.getFullText()));
        }
        for (Event event : events) {
            allId.add(event.getImage().getId());
            allId.addAll(getImagesId(event.getFullText()));
        }
        List<Image> images = (List<Image>) imageRepository.findAll();
        for (Image image : images) {
            if (image.getId() == 1) {
                continue;
            }
            if (!allId.contains(image.getId())) {
                delete(image);
            }
        }
    }

    public void delete(Image image) {
        File file = new File(image.getPath());
        file.delete();
        imageRepository.deleteById(image.getId());
    }

    private TreeSet<Long> getImagesId(String fullText) {
        TreeSet<Long> imagesId = new TreeSet<>();
        Pattern pattern = Pattern.compile("image\\/(\\d+)");
        Matcher matcher = pattern.matcher(fullText);
        while (matcher.find()) {
            imagesId.add(Long.parseLong(matcher.group(1)));
        }
        return imagesId;
    }
}
