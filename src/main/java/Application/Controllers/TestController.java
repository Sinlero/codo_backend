package Application.Controllers;

import Application.Servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    UserService userService;

    @RequestMapping("/seq")
    public String seq(){
        return "test";
    }

    @RequestMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok(userService.getAll());
    }




}
