package Application.Configuration;


import Application.Utils.Color.ColorCodeGenerator;
import Application.Utils.Color.HsvColorGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ColorCodeGenerator colorCodeGenerator() {
        return new HsvColorGenerator();
    }
}
