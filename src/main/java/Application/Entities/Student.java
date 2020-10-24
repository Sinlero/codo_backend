package Application.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student extends User{
    private BigDecimal balance;
    private String course;
    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<Discipline> disciplines;
}
