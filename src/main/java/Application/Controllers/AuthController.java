package Application.Controllers;

import Application.Data.Repositories.UserRepositories.AdministratorRepository;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/login")
    public ResponseEntity auth() {
        try {
            String name = null;
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            logger.info(authentication.getName() + " login with role " + authentication.getAuthorities());
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                switch (authority.getAuthority()) {
                    case "ROLE_USER": name = studentRepository.findStudentByLogin(authentication.getName()).get().getFio(); break;
                    case "ROLE_TEACHER": name = teacherRepository.findTeacherByLogin(authentication.getName()).get().getFio(); break;
                    case "ROLE_ADMIN": name = administratorRepository.findAdministratorByLogin(authentication.getName()).get().getFio(); break;
                }
            }
            return new ResponseEntity(authentication.getAuthorities() + " " + name, HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("wrong", ex);
        }
    }
}
