package Application.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String head;
    private String text;
    @OneToOne
    private Image image;
    private String date;

    public News(String head, String text, Image image, String date) {
        this.head = head;
        this.text = text;
        this.image = image;
        this.date = date;
    }
}
