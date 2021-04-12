package Application.Configuration;


import Application.Utils.Color.ColorCodeGenerator;
import Application.Utils.Color.HsvColorGenerator;
import org.apache.catalina.Context;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ColorCodeGenerator colorCodeGenerator() {
        return new HsvColorGenerator();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                Rfc6265CookieProcessor rfc6265Processor = new Rfc6265CookieProcessor();
                rfc6265Processor.setSameSiteCookies("None");
                context.setCookieProcessor(rfc6265Processor);
            }
        };
    }
}
