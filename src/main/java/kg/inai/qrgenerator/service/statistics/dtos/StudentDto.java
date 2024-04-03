package kg.inai.qrgenerator.service.statistics.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private Long absenceNum;
}
