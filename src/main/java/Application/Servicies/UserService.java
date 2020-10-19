package Application.Servicies;

import Application.Entities.User;
import Application.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }


    public User getById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getByLogin(String login) {
        Optional<User> user = userRepository.getUserByLogin(login);
        return user.orElse(null);
    }

    public List<User> getByPrivilege(int value) {
        List<User> user = userRepository.getUserByPrivilege(value);
        return user;
    }
}
