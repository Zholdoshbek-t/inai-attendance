package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Tag(name = "Group API", description = "API для работы с группами")
public class GroupController {

    private final GroupService groupService;

    @Operation(summary = "Создание группы")
    @PostMapping("/{name}")
    public ResponseEntity<RestResponse> createGroup(@PathVariable String name) {

        return ResponseEntity.ok(groupService.createGroup(name));
    }

    @Operation(summary = "Добавление студента по (айди студента, айди группы)")
    @PutMapping("/{studentId}/{groupId}")
    public ResponseEntity<RestResponse> addStudentToGroup(@PathVariable Long studentId,
                                                          @PathVariable Long groupId) {

        return ResponseEntity.ok(groupService.addStudentToGroup(studentId, groupId));
    }

    @Operation(summary = "Удаление студента по (айди студента, айди группы)")
    @DeleteMapping("/{studentId}/{groupId}")
    public ResponseEntity<RestResponse> removeStudentFromGroup(@PathVariable Long studentId,
                                                               @PathVariable Long groupId) {

        return ResponseEntity.ok(groupService.removeStudentFromGroup(studentId, groupId));
    }

    @Operation(summary = "Получение списка групп")
    @GetMapping("/all")
    public ResponseEntity<RestResponse> getGroups() {

        return ResponseEntity.ok(groupService.getGroups());
    }
}
