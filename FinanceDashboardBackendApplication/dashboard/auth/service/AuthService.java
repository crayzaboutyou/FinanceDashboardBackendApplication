package com.finance.dashboard.auth.service;

import com.finance.dashboard.auth.dto.AuthResponse;
import com.finance.dashboard.auth.dto.LoginRequest;
import com.finance.dashboard.security.service.JwtService;
import com.finance.dashboard.user.dto.UserResponse;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.mapper.UserMapper;
import com.finance.dashboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        UserResponse userResponse = userMapper.toResponse(user);

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .user(userResponse)
                .build();
    }
}
