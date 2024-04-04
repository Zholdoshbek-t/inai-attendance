package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.subject.SubjectService;
import kg.inai.qrgenerator.service.subject.dtos.SubjectDto;
import kg.inai.qrgenerator.service.subject.dtos.SubjectScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@Tag(name = "Subject API", description = "API для предметов")
public class SubjectController {

    private final SubjectService subjectService;

    @Operation(summary = "Создание предмета")
    @PostMapping
    public ResponseEntity<RestResponse> createSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.createSubject(subjectDto));
    }

    @Operation(summary = "Создание определенной пары")
    @PostMapping("/schedule")
    public ResponseEntity<RestResponse> createSubjectSchedule(@RequestBody SubjectScheduleDto subjectScheduleDto) {
        return ResponseEntity.ok(subjectService.createSubjectSchedule(subjectScheduleDto));
    }

    @Operation(summary = "Изменение определенной пары")
    @PutMapping("/schedule/{id}")
    public ResponseEntity<RestResponse> updateSubjectSchedule(@PathVariable Long id,
                                                              @RequestBody SubjectScheduleDto subjectScheduleDto) {
        return ResponseEntity.ok(subjectService.updateSubjectSchedule(id, subjectScheduleDto));
    }
}
