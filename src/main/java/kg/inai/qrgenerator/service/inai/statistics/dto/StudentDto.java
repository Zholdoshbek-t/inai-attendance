package kg.inai.qrgenerator.service.inai.statistics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {

    private Long id;

    private String fullName;

    private Long absenceNum;

    private String password;
}
