package Application.Servicies;

import Application.Entities.Discipline;
import Application.Repositories.DisciplineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {

    DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<Discipline> getAllDisciplines(){
        return (List<Discipline>) disciplineRepository.findAll();
    }

}
