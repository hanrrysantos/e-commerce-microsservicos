package br.com.hanrry.auth_service.client;

import br.com.hanrry.auth_service.dto.response.UserClientResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserClient {

    @GetMapping("/api/users/internal/email")
    UserClientResponseDTO findByEmail(@RequestParam String email);
}
