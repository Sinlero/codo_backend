package Application.Entities.UserEntities;

import Application.Entities.Discipline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Teacher extends AbstractUser {

    @ManyToMany
    private List<Discipline> disciplines;
    private String phoneNumber;
}
