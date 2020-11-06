package Application;

import Application.Servicies.FileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        FileService.init();
        SpringApplication.run(Application.class, args);
    }
}
