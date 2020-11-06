package Application.Controllers;

import Application.Servicies.ImageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/upload")
    public Long uploadImage (MultipartFile file) {
        return imageService.uploadImage(file);
    }

}
