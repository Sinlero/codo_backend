package Application.Configuration.Security;

import Application.Data.Repositories.UserRepositories.AdministratorRepository;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.UserEntities.Administrator;
import Application.Entities.UserEntities.Student;
import Application.Entities.UserEntities.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@org.springframework.context.annotation.Configuration
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Configuration extends WebSecurityConfigurerAdapter {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AdministratorRepository administratorRepository;

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
                .antMatchers("/*/public/**").permitAll()
                .antMatchers("/**").authenticated()
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
                return new User(student.getLogin(), "{noop}" + student.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        AuthorityUtils.createAuthorityList("ROLE_USER"));

            } else if (teacherRepository.findTeacherByLogin(userName).isPresent()) {
                Teacher teacher = teacherRepository.findTeacherByLogin(userName).get();
                return new User(teacher.getLogin(), "{noop}" + teacher.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        AuthorityUtils.createAuthorityList("ROLE_TEACHER"));

            } else if (administratorRepository.findAdministratorByLogin(userName).isPresent()) {
                Administrator administrator = administratorRepository.findAdministratorByLogin(userName).get();
                return new User(administrator.getLogin(), "{noop}" + administrator.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
            }
            return null;
        });
    }
}
