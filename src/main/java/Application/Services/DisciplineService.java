package Application.Services;

import Application.Entities.Discipline;
import Application.Data.Repositories.DisciplineRepository;
import Application.Utils.Color.ColorCodeGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;
    private ColorCodeGenerator colorCodeGenerator;

    public DisciplineService(DisciplineRepository disciplineRepository, ColorCodeGenerator colorCodeGenerator){
        this.disciplineRepository = disciplineRepository;
        this.colorCodeGenerator = colorCodeGenerator;
    }

    public List<Discipline> getAll() {
        return (List<Discipline>) disciplineRepository.findAll();
    }

    @Transactional
    public ResponseEntity<String> add(String name, BigDecimal cost, String colorCode) {
        if (colorCode == null || colorCode.isEmpty()) {
            colorCode = colorCodeGenerator.getColor();
        }
        Discipline discipline = new Discipline(name, cost, colorCode);
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

    @Transactional
    public ResponseEntity<String> update(Long id, String name, BigDecimal cost, String colorCode) {
        Optional<Discipline> discipline = disciplineRepository.findById(id);
        if (!discipline.isPresent()) {
            return new ResponseEntity<>("Discipline with this id not found", HttpStatus.NOT_FOUND);
        }
        Discipline updatedDiscipline = discipline.get();
        updatedDiscipline.setName(name);
        updatedDiscipline.setCost(cost);
        updatedDiscipline.setColorCode(colorCode);
        disciplineRepository.save(updatedDiscipline);
        return new ResponseEntity<>("Discipline updated",HttpStatus.OK);
    }

}
