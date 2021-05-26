package Application.Controllers;

import Application.Data.Repositories.UserRepositories.AdministratorRepository;
import Application.Data.Repositories.UserRepositories.StudentRepository;
import Application.Data.Repositories.UserRepositories.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {

    private AdministratorRepository administratorRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;

    public AuthController(AdministratorRepository administratorRepository, TeacherRepository teacherRepository,
                          StudentRepository studentRepository) {
        this.administratorRepository = administratorRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @RequestMapping("/login")
    public ResponseEntity auth() {
        try {
            String name = null;
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                switch (authority.getAuthority()) {
                    case "ROLE_USER": name = studentRepository.findStudentByLogin(authentication.getName()).get().getFio(); break;
                    case "ROLE_TEACHER": name = teacherRepository.findTeacherByLogin(authentication.getName()).get().getFio(); break;
                    case "ROLE_ADMIN": name = administratorRepository.findAdministratorByLogin(authentication.getName()).get().getFio(); break;
                }
            }
            List<String> responseObjects = new ArrayList<>();
            responseObjects.add(authentication.getAuthorities().toString());
            responseObjects.add(name);
            return new ResponseEntity(responseObjects, HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("wrong", ex);
        }
    }
}
