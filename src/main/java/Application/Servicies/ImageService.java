package Application.Servicies;

import Application.Entities.Image;
import Application.Data.Repositories.ImageRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
public class ImageService {

    private ImageRepository imageRepository;

    public ImageService (ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Long uploadImage(MultipartFile file) {
        File newFile = new File(String.valueOf(FileService.FILES_PATH));
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        if (file == null) {
            return null;
        } else {
            newFile = FileService.saveImage(newFile, file);
            Image image = new Image(newFile.getAbsolutePath());
            imageRepository.save(image);
            return image.getId();
        }
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
}
