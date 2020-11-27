package Application.Entities.UserEntities;

import Application.Entities.Discipline;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends AbstractUser {
    private BigDecimal balance;
    private String course;
    private String parentFio;
    private String phoneNumber;
    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Discipline> disciplines;
}
