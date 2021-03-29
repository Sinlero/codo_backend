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
    protected Long id;
    @Column(unique = true)
    protected String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password;
    protected String fio;
    protected Boolean sex;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Integer privilege;
}
