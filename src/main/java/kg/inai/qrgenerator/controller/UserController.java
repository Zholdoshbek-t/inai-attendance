package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.user.UserService;
import kg.inai.qrgenerator.service.user.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "API для пользователей")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создание пользователя")
    @PostMapping
    public ResponseEntity<RestResponse> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @Operation(summary = "Активация пользователя")
    @PutMapping("/activate/{id}")
    public ResponseEntity<RestResponse> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.activateUser(id));
    }

    @Operation(summary = "Деактивация пользователя")
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<RestResponse> deactivateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deactivateUser(id));
    }
}
