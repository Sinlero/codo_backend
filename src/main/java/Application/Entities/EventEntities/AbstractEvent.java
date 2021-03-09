package Application.Entities.EventEntities;

import Application.Entities.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Long id;
    protected String head;
    protected String previewText;
    protected String fullText;
    @OneToOne
    protected Image image;
    protected LocalDate date;
}
