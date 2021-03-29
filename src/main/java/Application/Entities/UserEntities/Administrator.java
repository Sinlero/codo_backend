package Application.Entities.UserEntities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity(name = "administrators")
@Data
@EqualsAndHashCode(callSuper = false)
public class Administrator extends AbstractUser{
}
