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
    private Boolean presence;
    private Integer mark;

    public Journal(Student student, Lesson lesson, Boolean presence, Integer mark) {
        this.student = student;
        this.lesson = lesson;
        this.presence = presence;
        this.mark = mark;
    }
}
