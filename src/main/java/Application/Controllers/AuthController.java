package Application.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/login")
    public ResponseEntity auth() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            logger.info(authentication.getName() + " login with role " + authentication.getAuthorities());
            return new ResponseEntity(authentication.getAuthorities(), HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("wrong", ex);
        }
    }
}
