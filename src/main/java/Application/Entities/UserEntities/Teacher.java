package Application.Entities.UserEntities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "teachers")
@Data
@NoArgsConstructor
public class Teacher extends AbstractUser {

}
