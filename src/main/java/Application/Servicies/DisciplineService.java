package Application.Servicies;

import Application.Entities.Discipline;
import Application.Repositories.DisciplineRepository;
import Application.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {

    @Autowired
    DisciplineRepository disciplineRepository;

    public List<Discipline> getAllDisciplines(){
        return (List<Discipline>) disciplineRepository.findAll();
    }

}
