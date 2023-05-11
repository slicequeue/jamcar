package com.slicequeue.jamcar.user.command.application;

import com.slicequeue.jamcar.common.exception.BadRequestException;
import com.slicequeue.jamcar.common.jwt.JwtTokenProvider;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserRepository;
import com.slicequeue.jamcar.user.command.domain.vo.Email;
import com.slicequeue.jamcar.user.command.domain.vo.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        Email email = new Email(loginUserRequest.getEmail());
        Password password = new Password(loginUserRequest.getPassword());
        User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("사용자 계정 정보를 확인해주세요."));
        if (!user.checkPassword(password)) {
            throw new BadRequestException("사용자 계정 정보를 확인해주세요.");
        }
        // JWT 액세스 토큰 발행
        Map<String, Object> token = jwtTokenProvider.createToken(user.getUserUid().toString(), user.getRoles());
        return LoginUserResponse.builder()
                .accessToken((String) token.get("accessToken"))
                .expiredAt((Instant) token.get("expiredAt"))
                .build();
    }
}
