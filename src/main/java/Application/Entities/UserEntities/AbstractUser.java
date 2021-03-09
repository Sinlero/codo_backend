package Application.Entities.UserEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String fio;
    private Boolean sex;
    private Integer privilege;
}
