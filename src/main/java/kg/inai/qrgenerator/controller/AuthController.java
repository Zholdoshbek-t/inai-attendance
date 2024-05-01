package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.inai.auth.AuthenticationService;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthReqDto;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kg.inai.qrgenerator.commons.constants.Endpoints.AUTH_URL;

@RestController
@RequestMapping(AUTH_URL)
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "API для работы с авторизацией")
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@RequestBody AuthReqDto authReqDto) {
        return ResponseEntity.ok(authService.login(authReqDto));
    }
}
