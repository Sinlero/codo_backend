package Application.Entities;

import Application.Entities.UserEntities.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "journal")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Student student;
    @OneToOne
    private Lesson lesson;
    private boolean presence;
    private Integer mark;
}
