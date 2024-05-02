package kg.inai.qrgenerator.service.inai.auth;

import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.commons.exception.ServerErrorException;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthReqDto;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthResDto;
import kg.inai.qrgenerator.service.inai.auth.dto.AuthResStudentDto;
import kg.inai.qrgenerator.service.utils.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public AuthResDto login(AuthReqDto authReqDto) {
        var user = userRepository.findByUsername(authReqDto.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!authReqDto.getPassword().equals(user.getPassword())) {
            throw new ServerErrorException(INCORRECT_PASSWORD);
        }

        return AuthResDto.builder()
                .userId(user.getId())
                .token(jwtProvider.generateToken(user))
                .role(user.getRole().name())
                .build();
    }

    public AuthResStudentDto loginStudent(AuthReqDto authReqDto) {
        var user = userRepository.findByUsername(authReqDto.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!authReqDto.getPassword().equals(user.getPassword())) {
            throw new ServerErrorException(INCORRECT_PASSWORD);
        }

        return AuthResStudentDto.builder()
                .userId(user.getId())
                .token(jwtProvider.generateToken(user))
                .groupId(user.getGroup().getId())
                .build();
    }
}
