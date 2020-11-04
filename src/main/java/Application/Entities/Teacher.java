package Application.Entities;

import lombok.Data;


import javax.persistence.Entity;


@Entity(name = "teachers")
@Data
public class Teacher extends AbstractUser {

}
