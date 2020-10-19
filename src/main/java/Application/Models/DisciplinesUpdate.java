package Application.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisciplinesUpdate {
    private Long userId;
    private ArrayList<Long> disciplines;
}
