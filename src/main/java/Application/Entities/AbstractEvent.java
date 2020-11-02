package Application.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEvent {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String head;
    private String previewText;
    private String fullText;
    @OneToOne
    private Image image;
    private LocalDateTime date;
}
