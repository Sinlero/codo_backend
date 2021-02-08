package Application.Servicies;

import Application.Data.Repositories.TeacherRepository;
import Application.Entities.Discipline;
import Application.Data.Repositories.DisciplineRepository;
import Application.Entities.UserEntities.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {

    DisciplineRepository disciplineRepository;
    TeacherRepository teacherRepository;

    public DisciplineService(DisciplineRepository disciplineRepository, TeacherRepository teacherRepository) {
        this.disciplineRepository = disciplineRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Discipline> getAll(){
        return (List<Discipline>) disciplineRepository.findAll();
    }

    public ResponseEntity<String> addDiscipline(@RequestParam String name, @RequestParam BigDecimal cost,
                                        @RequestParam ArrayList<Long> teacherIDs, @RequestParam String colorCode) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        for (Long teacherID : teacherIDs) {
            Optional<Teacher> teacher = teacherRepository.findById(teacherID);
            if(!teacher.isPresent()) {
                return new ResponseEntity<>(String.format("Teacher with id %s not found", teacherID), HttpStatus.NOT_FOUND);
            }
            teachers.add(teacher.get());
        }
        Discipline discipline = new Discipline(name, cost, teachers, colorCode);
        disciplineRepository.save(discipline);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
