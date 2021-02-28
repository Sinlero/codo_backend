package Application.Entities.UserEntities;

import Application.Entities.Discipline;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity(name = "teachers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Teacher extends AbstractUser {

    @ManyToMany
    private List<Discipline> disciplines;
    private String phoneNumber;
}
