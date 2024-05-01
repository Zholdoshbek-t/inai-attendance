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

    @Operation(summary = "Создание админа")
    @PostMapping("/admin")
    public ResponseEntity<RestResponse> createAdmin(@RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.createAdmin(userDto));
    }

    @Operation(summary = "Создание студента")
    @PostMapping("/student")
    public ResponseEntity<RestResponse> createStudent(@RequestParam Long groupId,
                                                      @RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.createStudent(groupId, userDto));
    }

    @Operation(summary = "Создание учителя")
    @PostMapping("/teacher")
    public ResponseEntity<RestResponse> createTeacher(@RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.createTeacher(userDto));
    }

    @Operation(summary = "Изменение группы студента")
    @PutMapping("/student/group")
    public ResponseEntity<RestResponse> updateStudentGroup(@RequestParam Long studentId,
                                                   @RequestParam Long groupId) {

        return ResponseEntity.ok(userService.updateStudentGroup(studentId, groupId));
    }

    @Operation(summary = "Изменение фио пользователя")
    @PutMapping("/name")
    public ResponseEntity<RestResponse> updateUser(@RequestParam Long id,
                                                   @RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @Operation(summary = "Изменение пароля пользователя")
    @PutMapping("/password")
    public ResponseEntity<RestResponse> updatePassword(@RequestParam Long id,
                                                       @RequestBody TextDto textDto) {

        return ResponseEntity.ok(userService.updatePassword(id, textDto));
    }

    @Operation(summary = "Активация пользователя")
    @PutMapping("/activate")
    public ResponseEntity<RestResponse> activateUser(@RequestParam Long id) {

        return ResponseEntity.ok(userService.activateUser(id));
    }

    @Operation(summary = "Деактивация пользователя")
    @PutMapping("/deactivate")
    public ResponseEntity<RestResponse> deactivateUser(@RequestParam Long id) {

        return ResponseEntity.ok(userService.deactivateUser(id));
    }
}
