package kg.inai.qrgenerator.service.inai.user;

import kg.inai.qrgenerator.commons.enums.Role;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.entity.repository.GroupRepository;
import kg.inai.qrgenerator.service.inai.user.dto.TextDto;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.inai.user.dto.UserDto;
import kg.inai.qrgenerator.service.utils.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public RestResponse createUser(UserDto userDto) {
        var username = userDto.getFirstName() + "." + userDto.getLastName();

        if(userRepository.existsByUsername(username)) {
            username += User.duplicateId.getAndIncrement();
        }

        var user = User.builder()
                .username(username)
                .password(username + generateRandomDigits())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .middleName(userDto.getMiddleName() != null ? userDto.getMiddleName() : "")
                .valid(true)
                .role(Role.valueOf(userDto.getRole()))
                .build();

        userRepository.save(user);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse createStudent(UserDto userDto) {
        var group = groupRepository.findById(userDto.getGroupId())
                .orElseThrow(() -> new NotFoundException(GROUP_NOT_FOUND));

        var username = userDto.getFirstName() + "." + userDto.getLastName();

        if(userRepository.existsByUsername(username)) {
            username += User.duplicateId.getAndIncrement();
        }

        var user = User.builder()
                .username(username)
                .password(username + generateRandomDigits())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .middleName(userDto.getMiddleName() != null ? userDto.getMiddleName() : "")
                .valid(true)
                .group(group)
                .role(Role.valueOf(userDto.getRole()))
                .build();

        userRepository.save(user);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if(!userDto.getFirstName().isEmpty()) {
            user.setFirstName(userDto.getFirstName());
        }
        if(!userDto.getMiddleName().isEmpty()) {
            user.setMiddleName(userDto.getMiddleName());
        }
        if(!userDto.getLastName().isEmpty()) {
            user.setLastName(userDto.getLastName());
        }

        userRepository.save(user);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse updatePassword(Long id, TextDto textDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if(!textDto.getText().isEmpty()) {
            user.setPassword(textDto.getText());
        }

        userRepository.save(user);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse activateUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        user.setValid(true);
        userRepository.save(user);

        return ResponseMapper.responseSuccess();
    }

    public RestResponse deactivateUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        user.setValid(false);
        userRepository.save(user);

        return ResponseMapper.responseSuccess();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDb = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return new org.springframework.security.core.userdetails
                .User(username, userDb.getPassword(), userDb.getAuthorities());
    }

    public String generateRandomDigits() {
        var random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
