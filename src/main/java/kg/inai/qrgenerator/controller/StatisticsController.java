package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.qr.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kg.inai.qrgenerator.commons.constants.Endpoints.STATISTICS_URL;

@RestController
@RequestMapping(STATISTICS_URL)
@RequiredArgsConstructor
@Tag(name = "Statistics API", description = "API для статистики")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Получение списка студентов по (айди группы, семестру, году)")
    @GetMapping("/students")
    public ResponseEntity<RestResponse> getStudentsList(Long groupId, Integer semester, Integer year) {
        return ResponseEntity.ok(statisticsService.getStudentsRatingLow(groupId, semester, year));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, семестру, году)" +
            " в порядке негативной посещаемости студентов")
    @GetMapping("/students/low")
    public ResponseEntity<RestResponse> getStudentsRatingLow(Long groupId, Integer semester, Integer year) {
        return ResponseEntity.ok(statisticsService.getStudentsRatingLow(groupId, semester, year));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, семестру, году)" +
            " в порядке положительной посещаемости студентов")
    @GetMapping("/students/high")
    public ResponseEntity<RestResponse> getStudentsRatingHigh(Long groupId, Integer semester, Integer year) {
        return ResponseEntity.ok(statisticsService.getStudentsRatingHigh(groupId, semester, year));
    }
}
