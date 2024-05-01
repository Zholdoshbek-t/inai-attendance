package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.inai.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.inai.qrgenerator.commons.constants.Endpoints.ATTENDANCE_URL;

@RestController
@RequestMapping(ATTENDANCE_URL)
@RequiredArgsConstructor
@Tag(name = "Attendance API", description = "API для работы с посещаемостью")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "Регистрация студента в определенную запись пары по (айди пары, айди студента)")
    @PutMapping
    public ResponseEntity<RestResponse> attendTheClass(@RequestParam Long attendanceId,
                                                       @RequestParam Long studentId) {

        return ResponseEntity.ok(attendanceService.attendTheClass(attendanceId, studentId));
    }

    @Operation(summary = "Получение списка присутствующих на сегодняшней паре")
    @GetMapping
    public ResponseEntity<List<String>> getClassAttendanceBySubjectSchedule(@RequestParam Long subjectScheduleId) {

        return ResponseEntity.ok(attendanceService.getCurrentClassAttendanceBySubjectSchedule(subjectScheduleId));
    }
}
