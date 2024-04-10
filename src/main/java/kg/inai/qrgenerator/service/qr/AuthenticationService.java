package kg.inai.qrgenerator.service.qr;

import kg.inai.qrgenerator.commons.exception.NotFoundException;
import kg.inai.qrgenerator.commons.exception.ServerErrorException;
import kg.inai.qrgenerator.controller.dto.RestResponse;
import kg.inai.qrgenerator.entity.repository.UserRepository;
import kg.inai.qrgenerator.service.qr.dto.AuthReqDto;
import kg.inai.qrgenerator.service.qr.dto.AuthResDto;
import kg.inai.qrgenerator.service.utils.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kg.inai.qrgenerator.commons.enums.SystemCode.*;
import static kg.inai.qrgenerator.commons.enums.SystemCode.SUCCESS;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public RestResponse login(AuthReqDto authReqDto) {
        var user = userRepository.findByUsername(authReqDto.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(authReqDto.getPassword(), user.getPassword())) {
            throw new ServerErrorException(INCORRECT_PASSWORD);
        }

        return RestResponse.builder()
                .message(SUCCESS.getMessage())
                .code(SUCCESS.getCode())
                .body(AuthResDto.builder()
                        .userId(user.getId())
                        .token(jwtProvider.generateToken(user))
                        .build())
                .build();
    }
}
