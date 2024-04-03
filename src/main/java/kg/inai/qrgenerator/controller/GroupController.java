package kg.inai.qrgenerator.controller;

import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/{name}")
    public ResponseEntity<RestResponse> createGroup(@PathVariable String name) {
        
        return ResponseEntity.ok(groupService.createGroup(name));
    }

    @PatchMapping("/{studentId}/{groupId}")
    public ResponseEntity<RestResponse> addStudentToGroup(@PathVariable Long studentId,
                                                          @PathVariable Long groupId) {
        
        return ResponseEntity.ok(groupService.addStudentToGroup(studentId, groupId));
    }

    @DeleteMapping("/{studentId}/{groupId}")
    public ResponseEntity<RestResponse> removeStudentFromGroup(@PathVariable Long studentId,
                                                               @PathVariable Long groupId) {
        
        return ResponseEntity.ok(groupService.removeStudentFromGroup(studentId, groupId));
    }
}
