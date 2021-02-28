package Application.Servicies;

import Application.Data.Repositories.UserRepositories.TeacherRepository;
import Application.Entities.Discipline;
import Application.Data.Repositories.DisciplineRepository;
import Application.Entities.UserEntities.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;
    private TeacherRepository teacherRepository;

    public DisciplineService(DisciplineRepository disciplineRepository, TeacherRepository teacherRepository) {
        this.disciplineRepository = disciplineRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Discipline> getAll() {
        return (List<Discipline>) disciplineRepository.findAll();
    }

    @Transactional
    public ResponseEntity<String> addDiscipline(String name, BigDecimal cost, ArrayList<Long> teacherIDs,
                                                String colorCode) {
        if (colorCode == null || colorCode.isEmpty()) {
            colorCode = generateColor();
        }
        if (teacherIDs == null || teacherIDs.isEmpty()) {
            Discipline discipline = new Discipline(name, cost, colorCode);
            disciplineRepository.save(discipline);
            return new ResponseEntity<>("Discipline added", HttpStatus.OK);
        }
        ArrayList<Teacher> teachers = new ArrayList<>();
        for (Long teacherID : teacherIDs) {
            Optional<Teacher> teacher = teacherRepository.findById(teacherID);
            if (!teacher.isPresent()) {
                return new ResponseEntity<>(String.format("Teacher with id %s not found", teacherID), HttpStatus.NOT_FOUND);
            }
            teachers.add(teacher.get());
        }
        Discipline discipline = new Discipline(name, cost, teachers, colorCode);
        disciplineRepository.save(discipline);
        return new ResponseEntity<>("Discipline added", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> delete(Long id) {
        Optional<Discipline> discipline = disciplineRepository.findById(id);
        if (!discipline.isPresent()) {
            return new ResponseEntity<>("Discipline with this id not found", HttpStatus.NOT_FOUND);
        }
        disciplineRepository.deleteById(id);
        return new ResponseEntity<>("Discipline deleted", HttpStatus.OK);
    }

    public String generateColor() {
        StringBuilder color = new StringBuilder("#");
        String[] letters = "0123456789abcdef".split("");
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            color.append(letters[random.nextInt(letters.length)]);
        }
        return color.toString();
    }
}
