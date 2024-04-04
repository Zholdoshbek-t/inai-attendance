package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance API", description = "API для работы с посещаемостью")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "Регистрация студента в определенную запись пары по (айди пары, айди студента)")
    @PutMapping("/{attendanceId}/{studentId}")
    public ResponseEntity<RestResponse> attendTheClass(@PathVariable Long attendanceId,
                                                       @PathVariable Long studentId) {

        return ResponseEntity.ok(attendanceService.attendTheClass(attendanceId, studentId));
    }

    @Operation(summary = "Получение списка присутствующих на паре по (айди пары)")
    @GetMapping("/{attendanceId}")
    public ResponseEntity<List<String>> getClassAttendance(@PathVariable Long attendanceId) {

        return ResponseEntity.ok(attendanceService.getClassAttendance(attendanceId));
    }

    @Operation(summary = "Получение списка посещаемостей определенной группы между двумя датами")
    @GetMapping("/{groupId}/{from}/{till}")
    public ResponseEntity<RestResponse> getAttendances(
            @PathVariable Long groupId,
            @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable LocalDate from,
            @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable LocalDate till) {

        return ResponseEntity.ok(attendanceService.getAttendances(groupId, from, till));
    }
}
