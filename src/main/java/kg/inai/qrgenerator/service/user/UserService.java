package kg.inai.qrgenerator.service.user;

import kg.inai.qrgenerator.commons.enums.Role;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dtos.RestResponse;
import kg.inai.qrgenerator.service.user.dtos.UserDto;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public RestResponse createUser(UserDto userDto) {
        var username = userDto.getFirstName() + "." + userDto.getLastName();

        if(userRepository.existsByUsername(username)) {
            username += User.duplicateId.getAndIncrement();
        }

        var user = User.builder()
                .username(username)
                .password(username)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .middleName(userDto.getMiddleName() != null ? userDto.getMiddleName() : "")
                .valid(true)
                .role(Role.valueOf(userDto.getRole()))
                .build();

        userRepository.save(user);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

    public RestResponse activateUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        user.setValid(true);
        userRepository.save(user);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

    public RestResponse deactivateUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        user.setValid(false);
        userRepository.save(user);

        return RestResponse.builder().message(SUCCESS.getMessage()).code(SUCCESS.getCode()).build();
    }

//    public User getAuthentication() {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        return userRepository.findByUsername(authentication.getName())
//                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
//    }
}
