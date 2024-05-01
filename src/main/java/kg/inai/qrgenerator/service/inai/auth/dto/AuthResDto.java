package kg.inai.qrgenerator.service.inai.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResDto {

    private Long userId;

    private String token;

    private String role;
}
