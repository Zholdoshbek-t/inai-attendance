package kg.inai.qrgenerator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.inai.statistics.StatisticsService;
import kg.inai.qrgenerator.service.inai.statistics.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static kg.inai.qrgenerator.commons.constants.Endpoints.STATISTICS_URL;

@RestController
@RequestMapping(STATISTICS_URL)
@RequiredArgsConstructor
@Tag(name = "Statistics API", description = "API для статистики")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Получение списка студентов по (айди группы, датам) в сортировке имен")
    @GetMapping("/name/{groupId}/{from}/{till}")
    public ResponseEntity<List<StudentDto>> getByFullName(@PathVariable Long groupId,
                                                      @PathVariable LocalDate from,
                                                      @PathVariable LocalDate till) {

        return ResponseEntity.ok(statisticsService.getByFullName(groupId, from, till));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, датам) в возрастающей сортировке посещаемости")
    @GetMapping("/score/asc/{groupId}/{from}/{till}")
    public ResponseEntity<List<StudentDto>> getByScoreAsc(@PathVariable Long groupId,
                                                      @PathVariable LocalDate from,
                                                      @PathVariable LocalDate till) {

        return ResponseEntity.ok(statisticsService.getByScoreAsc(groupId, from, till));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, датам) в возрастающей сортировке посещаемости")
    @GetMapping("/score/desc/{groupId}/{from}/{till}")
    public ResponseEntity<List<StudentDto>> getByScoreDesc(@PathVariable Long groupId,
                                                       @PathVariable LocalDate from,
                                                       @PathVariable LocalDate till) {

        return ResponseEntity.ok(statisticsService.getByScoreDesc(groupId, from, till));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, предмета, датам) в сортировке имен")
    @GetMapping("/name/subject/{groupId}/{from}/{till}/{subjectId}")
    public ResponseEntity<List<StudentDto>> getByFullNameSubjectId(@PathVariable Long groupId,
                                                                   @PathVariable LocalDate from,
                                                                   @PathVariable LocalDate till,
                                                                   @PathVariable Long subjectId) {

        return ResponseEntity.ok(statisticsService.getByFullNameSubjectId(groupId, from, till, subjectId));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, предмета, датам)" +
            " в возрастающей сортировке посещаемости")
    @GetMapping("/score/asc/subject/{groupId}/{from}/{till}/{subjectId}")
    public ResponseEntity<List<StudentDto>> getByScoreAscSubjectId(@PathVariable Long groupId,
                                                      @PathVariable LocalDate from,
                                                      @PathVariable LocalDate till,
                                                      @PathVariable Long subjectId) {

        return ResponseEntity.ok(statisticsService.getByScoreAscSubjectId(groupId, from, till, subjectId));
    }

    @Operation(summary = "Получение списка студентов по (айди группы, предмета, датам)" +
            " в возрастающей сортировке посещаемости")
    @GetMapping("/score/desc/subject/{groupId}/{from}/{till}/{subjectId}")
    public ResponseEntity<List<StudentDto>> getByScoreDescSubjectId(@PathVariable Long groupId,
                                                       @PathVariable LocalDate from,
                                                       @PathVariable LocalDate till,
                                                       @PathVariable Long subjectId) {

        return ResponseEntity.ok(statisticsService.getByScoreDescSubjectId(groupId, from, till, subjectId));
    }
}
