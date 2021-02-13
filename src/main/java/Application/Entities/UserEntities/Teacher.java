package Application.Entities.UserEntities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "teachers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Teacher extends AbstractUser {

    private String phoneNumber;
}
