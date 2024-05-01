package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.inai.subject.SubjectService;
import kg.inai.qrgenerator.service.inai.subject.dto.ClassDto;
import kg.inai.qrgenerator.service.inai.subject.dto.SubjectDto;
import kg.inai.qrgenerator.service.inai.subject.dto.SubjectScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static kg.inai.qrgenerator.commons.constants.Endpoints.SUBJECT_URL;

@RestController
@RequestMapping(SUBJECT_URL)
@RequiredArgsConstructor
@Tag(name = "Subject API", description = "API для предметов")
public class SubjectController {

    private final SubjectService subjectService;

    @Operation(summary = "Создание предмета")
    @PostMapping
    public ResponseEntity<RestResponse> createSubject(@RequestBody SubjectDto subjectDto) {

        return ResponseEntity.ok(subjectService.createSubject(subjectDto));
    }

    @Operation(summary = "Изменение предмета")
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse> updateSubject(@PathVariable Long id,
                                                      @RequestBody SubjectDto subjectDto) {

        return ResponseEntity.ok(subjectService.updateSubject(id, subjectDto));
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

    @Operation(summary = "Получение списка пар по году и семестру")
    @PutMapping("/{year}/{semester}")
    public ResponseEntity<RestResponse> getAllByYearAndSemester(@PathVariable Integer year,
                                                                @PathVariable Integer semester) {

        return ResponseEntity.ok(subjectService.getAllByYearAndSemester(year, semester));
    }

    @Operation(summary = "Получение списка пар учителя на сегодня")
    @GetMapping("/classes/today/{teacherId}")
    public ResponseEntity<List<ClassDto>> getTeachersClassesToday(@PathVariable Long teacherId) {

        return ResponseEntity.ok(subjectService.getTeachersClassesToday(teacherId));
    }

    @Operation(summary = "Получение списка пар учителя на неделю")
    @GetMapping("/classes/week/{teacherId}")
    public ResponseEntity<Map<String, List<ClassDto>>> getTeachersWeekClasses(@PathVariable Long teacherId) {

        return ResponseEntity.ok(subjectService.getTeachersWeekClasses(teacherId));
    }
}
