package kg.inai.qrgenerator.service.subject;

import kg.inai.qrgenerator.commons.exception.AlreadyExistsException;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.subject.dtos.SubjectDto;
import kg.inai.qrgenerator.entity.Subject;
import kg.inai.qrgenerator.entity.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public RestResponse createSubject(SubjectDto subjectDto) {
        if (subjectRepository.existsByNameAndSemesterAndYear(subjectDto.getName(), subjectDto.getSemester(),
                subjectDto.getYear())) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var subject = Subject.builder()
                .name(subjectDto.getName())
                .semester(subjectDto.getSemester())
                .year(subjectDto.getYear())
                .build();

        subjectRepository.save(subject);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }
}
