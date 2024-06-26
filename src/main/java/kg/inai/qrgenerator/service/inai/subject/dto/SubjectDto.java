package kg.inai.qrgenerator.service.inai.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {

    private String name;

    private Integer year;

    private Integer semester;
}
