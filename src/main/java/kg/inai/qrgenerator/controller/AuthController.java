package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.service.inai.auth.AuthenticationService;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthReqDto;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthResDto;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthResStudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kg.inai.qrgenerator.commons.constants.Endpoints.AUTH_URL;

@CrossOrigin
@RestController
@RequestMapping(AUTH_URL)
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "API для работы с авторизацией")
public class AuthController {

    private final AuthenticationService authService;

    @Operation(summary = "Авторизация учителя/админа")
    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@RequestBody AuthReqDto authReqDto) {
        return ResponseEntity.ok(authService.login(authReqDto));
    }

    @Operation(summary = "Авторизация студента")
    @PostMapping("/login/student")
    public ResponseEntity<AuthResStudentDto> loginStudent(@RequestBody AuthReqDto authReqDto) {
        return ResponseEntity.ok(authService.loginStudent(authReqDto));
    }
}
