package Application.Data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TreeSet;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisciplinesUpdate {
    private Long userId;
    private TreeSet<Long> disciplines;
}
