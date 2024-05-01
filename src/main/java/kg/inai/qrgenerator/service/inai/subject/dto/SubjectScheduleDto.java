package kg.inai.qrgenerator.service.inai.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectScheduleDto {

    private Long classTimeId;

    private Long subjectId;

    private Long groupId;

    private Long teacherId;

    private Long classDayId;
}
