package kg.inai.qrgenerator.service.inai.options.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOptionDto {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String role;

    private String group;
}
