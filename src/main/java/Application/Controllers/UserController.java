package Application.Controllers;

import Application.Servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getUserById")
    public ResponseEntity getUserById(@RequestParam long id) {
        return ResponseEntity.ok(userService.getById(id) != null ? userService.getById(id) : "User with this id " +
                "not found");
    }

    @RequestMapping("/getUserByLogin")
    public ResponseEntity getUserById(@RequestParam String login) {
        return ResponseEntity.ok(userService.getByLogin(login) != null ? userService.getByLogin(login) : "User " +
                "with this login not found");
    }

    @RequestMapping("/getUserByPrivilege")
    public ResponseEntity getUserByPrivilege(@RequestParam int value) {
        return ResponseEntity.ok(userService.getByPrivilege(value) != null ? userService.getByPrivilege(value) : "User " +
                "with this privilege not found");
    }
}
