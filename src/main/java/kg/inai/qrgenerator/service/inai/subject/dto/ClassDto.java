package kg.inai.qrgenerator.service.inai.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {

    private Long subjectScheduleId;

    private String subjectName;

    private String groupName;

    private String classTime;
}
