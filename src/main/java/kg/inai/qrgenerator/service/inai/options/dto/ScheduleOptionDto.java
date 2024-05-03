package kg.inai.qrgenerator.service.inai.options.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleOptionDto {

    private Long subjectScheduleId;

    private String subjectName;

    private String classTime;

    private String classDay;

    private String teacher;

    private Long groupId;

    private String groupName;
}
