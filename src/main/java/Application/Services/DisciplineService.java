package Application.Services;

import Application.Data.Repositories.DisciplineRepository;
import Application.Entities.Discipline;
import Application.Utils.Color.ColorCodeGenerator;
import Application.Utils.Response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final ColorCodeGenerator colorCodeGenerator;
    private final ResponseUtil responseUtil;

    public DisciplineService(DisciplineRepository disciplineRepository, ColorCodeGenerator colorCodeGenerator, ResponseUtil responseUtil){
        this.disciplineRepository = disciplineRepository;
        this.colorCodeGenerator = colorCodeGenerator;
        this.responseUtil = responseUtil;
    }

    public List<Discipline> getAll() {
        return (List<Discipline>) disciplineRepository.findAll();
    }

    public ResponseEntity<String> add(String name, BigDecimal cost, String colorCode) {
        if (colorCode == null || colorCode.isEmpty() || colorCode.equals("null")) {
            colorCode = colorCodeGenerator.getColor();
        }
        Discipline discipline = new Discipline(name, cost, colorCode);
        disciplineRepository.save(discipline);
        return new ResponseEntity<>("Discipline added", HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Discipline> discipline = disciplineRepository.findById(id);
        if (!discipline.isPresent()) {
            return responseUtil.notFoundId("Discipline");
        }
        disciplineRepository.deleteById(id);
        return new ResponseEntity<>("Discipline deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Long id, String name, BigDecimal cost, String colorCode) {
        Optional<Discipline> discipline = disciplineRepository.findById(id);
        if (!discipline.isPresent()) {
            return responseUtil.notFoundId("Discipline");
        }
        Discipline updatedDiscipline = discipline.get();
        updatedDiscipline.setName(name);
        updatedDiscipline.setCost(cost);
        updatedDiscipline.setColorCode(colorCode);
        disciplineRepository.save(updatedDiscipline);
        return new ResponseEntity<>("Discipline updated",HttpStatus.OK);
    }

}
