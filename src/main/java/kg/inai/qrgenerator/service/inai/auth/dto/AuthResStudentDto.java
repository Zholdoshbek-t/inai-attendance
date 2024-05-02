package kg.inai.qrgenerator.service.inai.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResStudentDto {

    private Long userId;

    private String token;

    private Long groupId;
}
