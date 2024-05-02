package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.service.inai.options.OptionsService;
import kg.inai.qrgenerator.service.inai.options.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static kg.inai.qrgenerator.commons.constants.Endpoints.OPTIONS_URL;

@CrossOrigin
@RestController
@RequestMapping(OPTIONS_URL)
@RequiredArgsConstructor
@Tag(name = "Options API", description = "API для работы с опциями")
public class OptionsController {

    private final OptionsService optionsService;

    @Operation(summary = "Получение списка ролей")
    @GetMapping("/roles")
    public ResponseEntity<List<RoleOptionDto>> getRoles() {

        return ResponseEntity.ok(optionsService.getRoles());
    }

    @Operation(summary = "Получение списка учителей")
    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherOptionDto>> getTeachers() {

        return ResponseEntity.ok(optionsService.getTeachers());
    }

    @Operation(summary = "Получение списка предметов")
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectOptionDto>> getSubjects() {

        return ResponseEntity.ok(optionsService.getSubjects());
    }

    @Operation(summary = "Получение списка время занятий")
    @GetMapping("/class-times")
    public ResponseEntity<List<ClassTimeOptionDto>> getClassTimes() {

        return ResponseEntity.ok(optionsService.getClassTimes());
    }

    @Operation(summary = "Получение списка дней недели")
    @GetMapping("/class-days")
    public ResponseEntity<List<ClassDayOptionDto>> getClassDays() {

        return ResponseEntity.ok(optionsService.getClassDays());
    }

    @Operation(summary = "Получение списка групп")
    @GetMapping("/groups")
    public ResponseEntity<List<GroupOptionDto>> getGroups() {

        return ResponseEntity.ok(optionsService.getGroups());
    }

    @Operation(summary = "Получение списка студентов")
    @GetMapping("/students")
    public ResponseEntity<List<StudentOptionDto>> getStudents() {

        return ResponseEntity.ok(optionsService.getStudents());
    }
}
