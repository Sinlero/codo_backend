package Application.Entities;

import Application.Entities.UserEntities.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Discipline discipline;
    @OneToOne
    private Teacher teacher;
    private Integer lessonNumber;
    @Column(length = 500)
    private String homework;
    private String classroom;
    private LocalDate date;
}
