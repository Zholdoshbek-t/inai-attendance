package kg.inai.qrgenerator.service.attendance.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {

    private String date;

    private String dayOfWeek;

    private String classTime;

    private String subject;

    private String teacher;

    private String group;

    private int studentsCount;
}
