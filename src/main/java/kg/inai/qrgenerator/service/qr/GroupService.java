package kg.inai.qrgenerator.service.qr;

import kg.inai.qrgenerator.commons.exception.AlreadyExistsException;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.entity.Group;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.qr.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public RestResponse createGroup(String name) {
        if(groupRepository.existsByName(name)) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var group = Group.builder().name(name).build();

        groupRepository.save(group);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

    public RestResponse addStudentToGroup(Long studentId, Long groupId) {
        var user = userRepository.findById(studentId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        var group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        if(group.getStudents().contains(user)) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        group.getStudents().add(user);
        groupRepository.save(group);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

    public RestResponse removeStudentFromGroup(Long studentId, Long groupId) {
        var user = userRepository.findById(studentId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        var group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        if(group.getStudents().contains(user)) {
            throw new NotFoundException(STUDENT_NOT_FOUND_IN_GROUP);
        }

        group.getStudents().remove(user);
        groupRepository.save(group);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

    public RestResponse getGroups() {
        var groups = groupRepository.findAll().stream()
                .map(group -> GroupDto.builder().id(group.getId()).name(group.getName()).build())
                .sorted()
                .toList();

        return RestResponse.builder()
                .message(SUCCESS.getMessage())
                .code(SUCCESS.getCode())
                .body(groups)
                .build();
    }
}
