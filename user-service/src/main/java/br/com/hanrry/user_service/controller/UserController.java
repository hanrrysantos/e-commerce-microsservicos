package br.com.hanrry.user_service.controller;

import br.com.hanrry.user_service.dto.request.UpdateUserRequestDTO;
import br.com.hanrry.user_service.dto.request.UserRequestDTO;
import br.com.hanrry.user_service.dto.response.UserAuthResponseDTO;
import br.com.hanrry.user_service.dto.response.UserResponseDTO;
import br.com.hanrry.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody
            @Valid
            UserRequestDTO request){
        UserResponseDTO user = userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(
            @PathVariable UUID id
    ) {
        UserResponseDTO user = userService.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers(){
        List<UserResponseDTO> listAllUsers = userService.findAllUsers();
        return ResponseEntity.ok().body(listAllUsers);
    }

    @GetMapping(value = "/email")
    public ResponseEntity<UserResponseDTO> findAllUsers(@RequestParam String email){
        UserResponseDTO user = userService.findUserByEmail(email);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable UUID id,
            @Valid
            @RequestBody UpdateUserRequestDTO requestDTO
    ){
        UserResponseDTO user = userService.updateUser(id, requestDTO);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id
    ){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // endpoint interno — só o auth-service vai chamar
    @GetMapping("/internal/email")
    public ResponseEntity<UserAuthResponseDTO> findUserForAuth(@RequestParam String email) {
        UserAuthResponseDTO user = userService.findUserForAuth(email);
        return ResponseEntity.ok(user);
    }

}
