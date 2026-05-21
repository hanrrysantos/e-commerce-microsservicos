package br.com.hanrry.auth_service.service;

import br.com.hanrry.auth_service.client.UserClient;
import br.com.hanrry.auth_service.dto.request.AuthRequestDTO;
import br.com.hanrry.auth_service.dto.response.AuthResponseDTO;
import br.com.hanrry.auth_service.dto.response.UserClientResponseDTO;
import br.com.hanrry.auth_service.exception.InvalidEmailOrPassword;
import br.com.hanrry.auth_service.exception.UserIsNotActive;
import br.com.hanrry.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(AuthRequestDTO request){
        UserClientResponseDTO user = userClient.findByEmail(request.email());

        if(!user.status().equals("REGISTERED")){
            throw new UserIsNotActive("User is not active");
        }

        if (!passwordEncoder.matches(request.password(), user.password())) {
            throw new InvalidEmailOrPassword("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.email(), user.role());

        return new AuthResponseDTO(token, user.email(), user.role());
    }
}
