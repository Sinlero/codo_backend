package Application.Entities.UserEntities;

import Application.Entities.Discipline;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends AbstractUser {
    protected BigDecimal balance;
    protected String course;
    protected String parentFio;
    protected String phoneNumber;
    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected List<Discipline> disciplines;
    protected Boolean debtor;
}
