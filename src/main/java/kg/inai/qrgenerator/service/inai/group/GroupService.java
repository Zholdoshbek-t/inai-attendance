package kg.inai.qrgenerator.service.inai.group;

import kg.inai.qrgenerator.commons.exception.AlreadyExistsException;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.entity.Group;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.inai.group.dto.GroupDto;
import kg.inai.qrgenerator.service.inai.statistics.dto.StudentDto;
import kg.inai.qrgenerator.service.utils.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public List<StudentDto> getGroupList(Long groupId) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        if(group.getStudents() == null) {
            group.setStudents(new ArrayList<>());
        }

        return group.getStudents().stream()
                .map(student -> {
                    var strBuilder = new StringBuilder();
                    strBuilder.append(student.getLastName());
                    strBuilder.append(student.getFirstName());
                    if(!student.getMiddleName().isEmpty()) {
                        strBuilder.append(student.getMiddleName());
                    }

                    return StudentDto.builder()
                            .id(student.getId())
                            .fullName(strBuilder.toString())
                            .build();
                })
                .sorted(Comparator.comparing(StudentDto::getFullName))
                .toList();
    }

    public List<StudentDto> getGroupListAdmin(Long groupId) {
        var group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        if(group.getStudents() == null) {
            group.setStudents(new ArrayList<>());
        }

        return group.getStudents().stream()
                .map(student -> {
                    var strBuilder = new StringBuilder();
                    strBuilder.append(student.getLastName());
                    strBuilder.append(student.getFirstName());
                    if(!student.getMiddleName().isEmpty()) {
                        strBuilder.append(student.getMiddleName());
                    }

                    return StudentDto.builder()
                            .id(student.getId())
                            .fullName(strBuilder.toString())
                            .password(student.getPassword())
                            .build();
                })
                .sorted(Comparator.comparing(StudentDto::getFullName))
                .toList();
    }

    public RestResponse createGroup(String name) {
        if(groupRepository.existsByName(name)) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        var group = Group.builder().name(name.toUpperCase()).students(new ArrayList<>()).build();

        groupRepository.save(group);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse updateGroup(Long groupId, String name) {
        if(groupRepository.existsByName(name)) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        group.setName(name);

        groupRepository.save(group);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse addStudentToGroup(Long studentId, Long groupId) {
        var user = userRepository.findById(studentId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        var group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        if(group.getStudents() == null) {
            group.setStudents(new ArrayList<>());
        }

        if(group.getStudents().contains(user)) {
            throw new AlreadyExistsException(ALREADY_EXISTS);
        }

        group.getStudents().add(user);
        groupRepository.save(group);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse removeStudentFromGroup(Long studentId, Long groupId) {
        var user = userRepository.findById(studentId).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        var group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        if(group.getStudents() == null) {
            group.setStudents(new ArrayList<>());
        }

        if(group.getStudents().contains(user)) {
            throw new NotFoundException(STUDENT_NOT_FOUND_IN_GROUP);
        }

        group.getStudents().remove(user);
        groupRepository.save(group);

        return ResponseMapper.responseSuccess();
    }

    public List<GroupDto> getGroups() {
        return groupRepository.findAll().stream()
                .map(group -> GroupDto.builder().id(group.getId()).name(group.getName()).build())
                .sorted()
                .toList();
    }
}