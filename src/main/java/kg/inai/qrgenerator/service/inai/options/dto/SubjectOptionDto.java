package kg.inai.qrgenerator.service.inai.options.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectOptionDto {

    private Long id;

    private String name;

    private Integer year;

    private Integer semester;
}
