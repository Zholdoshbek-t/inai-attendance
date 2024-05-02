package kg.inai.qrgenerator.service.inai.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentClassDto {

    private Long subjectScheduleId;

    private String subjectName;

    private String classTime;
}
