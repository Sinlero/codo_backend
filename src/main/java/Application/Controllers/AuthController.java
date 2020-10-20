package Application.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping("/login")
    public ResponseEntity auth() {
        try {
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("wrong", ex);
        }
    }
}
