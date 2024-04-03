package kg.inai.qrgenerator.service.subject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectScheduleDto {

    private String classTime;

    private Long subjectId;

    private Long groupId;

    private Long teacherId;

    private String dayOfWeek;
}
