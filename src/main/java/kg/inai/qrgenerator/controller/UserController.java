package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.inai.user.UserService;
import kg.inai.qrgenerator.service.inai.user.dto.TextDto;
import kg.inai.qrgenerator.service.inai.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kg.inai.qrgenerator.commons.constants.Endpoints.USER_URL;


@RestController
@RequestMapping(USER_URL)
@RequiredArgsConstructor
@Tag(name = "User API", description = "API для пользователей")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создание пользователя")
    @PostMapping
    public ResponseEntity<RestResponse> createUser(@RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @Operation(summary = "Создание студента")
    @PostMapping("/teacher")
    public ResponseEntity<RestResponse> createStudent(@RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.createStudent(userDto));
    }

    @Operation(summary = "Изменение фио пользователя")
    @PutMapping("/name/{id}")
    public ResponseEntity<RestResponse> updateUser(@PathVariable Long id,
                                                   @RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @Operation(summary = "Изменение пароля пользователя")
    @PutMapping("/password/{id}")
    public ResponseEntity<RestResponse> updatePassword(@PathVariable Long id,
                                                       @RequestBody TextDto textDto) {

        return ResponseEntity.ok(userService.updatePassword(id, textDto));
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
