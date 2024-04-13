package kg.inai.qrgenerator.service.qr;

import kg.inai.qrgenerator.commons.enums.Role;
import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.service.qr.dto.UserDto;
import kg.inai.qrgenerator.entity.User;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDb = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return new org.springframework.security.core.userdetails
                .User(username, userDb.getPassword(), userDb.getAuthorities());
    }
}
