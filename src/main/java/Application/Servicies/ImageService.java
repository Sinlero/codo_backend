package Application.Servicies;

import Application.Entities.Image;
import Application.Repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ImageService {

    ImageRepository imageRepository;

    public ImageService (ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Long uploadImage(MultipartFile file) {
        File newFile = new File(System.getProperty("user.dir") + "/NewsImages/");
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        if (file == null) {
            return null;
        } else {
            newFile = NewsService.saveImage(newFile, file);
            Image image = new Image(newFile.getAbsolutePath());
            imageRepository.save(image);
            return image.getId();
        }
    }
}
