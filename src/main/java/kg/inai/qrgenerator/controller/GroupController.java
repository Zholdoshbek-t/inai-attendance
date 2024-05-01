package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.inai.group.GroupService;
import kg.inai.qrgenerator.service.inai.group.dto.GroupDto;
import kg.inai.qrgenerator.service.inai.statistics.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.inai.qrgenerator.commons.constants.Endpoints.GROUP_URL;

@RestController
@RequestMapping(GROUP_URL)
@RequiredArgsConstructor
@Tag(name = "Group API", description = "API для работы с группами")
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "Получение списка студентов определенной группы")
    @GetMapping
    public ResponseEntity<List<StudentDto>> getGroupList(@RequestParam Long groupId) {

        return ResponseEntity.ok(groupService.getGroupList(groupId));
    }

    @Operation(summary = "Получение списка студентов определенной группы с паролями")
    @GetMapping("/admin")
    public ResponseEntity<List<StudentDto>> getGroupListAdmin(@RequestParam Long groupId) {

        return ResponseEntity.ok(groupService.getGroupListAdmin(groupId));
    }

    @Operation(summary = "Создание группы")
    @PostMapping
    public ResponseEntity<RestResponse> createGroup(@RequestParam String name) {

        return ResponseEntity.ok(groupService.createGroup(name));
    }

    @Operation(summary = "Изменение названия группы")
    @PutMapping
    public ResponseEntity<RestResponse> updateGroupName(@RequestParam Long groupId,
                                                    @RequestParam String name) {

        return ResponseEntity.ok(groupService.updateGroup(groupId, name));
    }

    @Operation(summary = "Добавление студента по (айди студента, айди группы)")
    @PutMapping
    public ResponseEntity<RestResponse> addStudentToGroup(@RequestParam Long studentId,
                                                          @RequestParam Long groupId) {

        return ResponseEntity.ok(groupService.addStudentToGroup(studentId, groupId));
    }

    @Operation(summary = "Удаление студента по (айди студента, айди группы)")
    @DeleteMapping
    public ResponseEntity<RestResponse> removeStudentFromGroup(@RequestParam Long studentId,
                                                               @RequestParam Long groupId) {

        return ResponseEntity.ok(groupService.removeStudentFromGroup(studentId, groupId));
    }

    @Operation(summary = "Получение списка групп со студентами")
    @GetMapping("/all")
    public ResponseEntity<List<GroupDto>> getGroups() {

        return ResponseEntity.ok(groupService.getGroups());
    }
}
