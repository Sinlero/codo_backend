package Application.Controllers;

import Application.Services.ImageService;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/upload")
    public Long uploadImage (MultipartFile file) {
        return imageService.uploadImage(file);
    }

    @RequestMapping("/public/{id}")
    public HttpEntity<byte[]> getImage(@PathVariable Long id) {
        return imageService.getImage(id);
    }
}
