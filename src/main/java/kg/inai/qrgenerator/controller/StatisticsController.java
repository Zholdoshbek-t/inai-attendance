package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.service.inai.statistics.StatisticsService;
import kg.inai.qrgenerator.service.inai.statistics.dto.StudentStatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static kg.inai.qrgenerator.commons.constants.Endpoints.STATISTICS_URL;

@CrossOrigin
@RestController
@RequestMapping(STATISTICS_URL)
@RequiredArgsConstructor
@Tag(name = "Statistics API", description = "API для статистики")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Получение списка студентов по (айди группы, датам) в сортировке имен")
    @GetMapping("/name")
    public ResponseEntity<List<StudentStatisticsDto>> getByFullName(@RequestParam Long groupId,
                                                                    @RequestParam LocalDate from,
                                                                    @RequestParam LocalDate till) {

        return ResponseEntity.ok(statisticsService.getByFullName(groupId, from, till));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, датам) в возрастающей сортировке посещаемости")
    @GetMapping("/score/asc")
    public ResponseEntity<List<StudentStatisticsDto>> getByScoreAsc(@RequestParam Long groupId,
                                                      @RequestParam LocalDate from,
                                                      @RequestParam LocalDate till) {

        return ResponseEntity.ok(statisticsService.getByScoreAsc(groupId, from, till));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, датам) в возрастающей сортировке посещаемости")
    @GetMapping("/score/desc")
    public ResponseEntity<List<StudentStatisticsDto>> getByScoreDesc(@RequestParam Long groupId,
                                                       @RequestParam LocalDate from,
                                                       @RequestParam LocalDate till) {

        return ResponseEntity.ok(statisticsService.getByScoreDesc(groupId, from, till));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, предмета, датам) в сортировке имен")
    @GetMapping("/name/subject")
    public ResponseEntity<List<StudentStatisticsDto>> getByFullNameSubjectId(@RequestParam Long groupId,
                                                                   @RequestParam LocalDate from,
                                                                   @RequestParam LocalDate till,
                                                                   @RequestParam Long subjectId) {

        return ResponseEntity.ok(statisticsService.getByFullNameSubjectId(groupId, from, till, subjectId));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, предмета, датам)" +
            " в возрастающей сортировке посещаемости")
    @GetMapping("/score/asc/subject")
    public ResponseEntity<List<StudentStatisticsDto>> getByScoreAscSubjectId(@RequestParam Long groupId,
                                                      @RequestParam LocalDate from,
                                                      @RequestParam LocalDate till,
                                                      @RequestParam Long subjectId) {

        return ResponseEntity.ok(statisticsService.getByScoreAscSubjectId(groupId, from, till, subjectId));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, предмета, датам)" +
            " в возрастающей сортировке посещаемости")
    @GetMapping("/score/desc/subject")
    public ResponseEntity<List<StudentStatisticsDto>> getByScoreDescSubjectId(@RequestParam Long groupId,
                                                       @RequestParam LocalDate from,
                                                       @RequestParam LocalDate till,
                                                       @RequestParam Long subjectId) {

        return ResponseEntity.ok(statisticsService.getByScoreDescSubjectId(groupId, from, till, subjectId));
    }
}
