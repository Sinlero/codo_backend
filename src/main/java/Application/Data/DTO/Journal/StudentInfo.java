package Application.Data.DTO.Journal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentInfo {

        private Long studentId;
        private Boolean presence;
        private Integer mark;
}
