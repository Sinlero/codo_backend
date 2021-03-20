package Application.Configuration.Security;

import Application.Entities.UserEntities.Student;
import Application.Entities.UserEntities.Teacher;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@org.springframework.context.annotation.Configuration
@EnableGlobalAuthentication
public class Configuration extends WebSecurityConfigurerAdapter {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).authorizeRequests()
                .antMatchers("/seq").permitAll()
                .antMatchers("/test").authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userName -> {
                    if (studentRepository.findStudentByLogin(userName).isPresent()) {
                        Student student = studentRepository.findStudentByLogin(userName).get();
                        return new org.springframework.security.core.userdetails.User(
                                student.getLogin(),
                                "{noop}" + student.getPassword(), true, true, true, true,
                                AuthorityUtils.createAuthorityList("USER"));

                    } else if (teacherRepository.findTeacherByLogin(userName).isPresent()) {
                        Teacher teacher = teacherRepository.findTeacherByLogin(userName).get();
                        return new org.springframework.security.core.userdetails.User(
                                teacher.getLogin(),
                                "{noop}" + teacher.getPassword(), true, true, true, true,
                                AuthorityUtils.createAuthorityList("USER"));
                    }
                    return null;
                });
    }
}
