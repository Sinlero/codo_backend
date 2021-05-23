package Application.Data.DTO.Journal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JournalUpdate {

    private Long lessonId;
    private List<StudentInfo> journalList;
}
