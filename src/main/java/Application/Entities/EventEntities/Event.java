package Application.Entities.EventEntities;

import Application.Entities.Image;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name = "events")
@AllArgsConstructor
public class Event extends AbstractEvent {

    public Event(String head, String previewText, String fullText, Image image, LocalDate date) {
        this.head = head;
        this.previewText = previewText;
        this.fullText = fullText;
        this.image = image;
        this.date = date;
    }
}
