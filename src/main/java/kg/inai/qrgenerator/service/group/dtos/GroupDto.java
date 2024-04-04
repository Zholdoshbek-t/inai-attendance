package kg.inai.qrgenerator.service.group.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    private Long id;

    private String name;

    private Map<Long, String> studentIdFullName;
}
