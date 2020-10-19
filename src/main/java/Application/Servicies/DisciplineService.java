package Application.Servicies;

import Application.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {

    @Autowired
    StudentRepository studentRepository;

}
