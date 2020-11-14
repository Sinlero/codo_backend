package Application.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "news")
@AllArgsConstructor
@Data
public class News extends AbstractEvent {

    public News(String head, String previewText, String fullText, Image image, LocalDate date) {
        this.head = head;
        this.previewText = previewText;
        this.fullText = fullText;
        this.image = image;
        this.date = date;
    }
}
