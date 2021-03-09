package Application.Entities;

import Application.Entities.UserEntities.Student;
import Application.Entities.UserEntities.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "disciplines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal cost;
    @ManyToMany(mappedBy = "disciplines")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Teacher> teacher;
    @ManyToMany(mappedBy = "disciplines")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Student> student ;
    private String colorCode;

    public Discipline(String name, BigDecimal cost, String colorCode) {
        this.name = name;
        this.cost = cost;
        this.colorCode = colorCode;
    }
}
