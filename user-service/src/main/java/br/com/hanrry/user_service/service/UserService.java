package br.com.hanrry.user_service.service;

import br.com.hanrry.user_service.database.model.UserEntity;
import br.com.hanrry.user_service.database.repository.IUserRepository;
import br.com.hanrry.user_service.dto.event.UserEventDTO;
import br.com.hanrry.user_service.dto.request.UpdateUserRequestDTO;
import br.com.hanrry.user_service.dto.request.UserRequestDTO;
import br.com.hanrry.user_service.dto.response.UserAuthResponseDTO;
import br.com.hanrry.user_service.dto.response.UserResponseDTO;
import br.com.hanrry.user_service.enums.UserStatus;
import br.com.hanrry.user_service.exception.EmailAlreadyExistsException;
import br.com.hanrry.user_service.exception.UserNotFoundException;
import br.com.hanrry.user_service.mapper.IUserMapper;
import br.com.hanrry.user_service.producer.UserProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final UserProducer userProducer;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO request){

        if(userRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        UserEntity user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.password()));

        UserEntity savedUser = userRepository.save(user);

        UserEventDTO event = userMapper.toEventDTO(savedUser);

        userProducer.publishMessageEmail(event);

        return userMapper.toDTO(savedUser);
    }

    public UserResponseDTO findUserById(UUID id){
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );

        return userMapper.toDTO(user);
    }

    public List<UserResponseDTO> findAllUsers() {

        List<UserEntity> users = userRepository.findAll();

        return userMapper.toDTOList(users);
    }


    public UserResponseDTO findUserByEmail(String email){
        UserEntity  user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with email: " + email)
        );

        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(UUID id, UpdateUserRequestDTO updateUserRequestDTO){
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );

        user.setName(updateUserRequestDTO.name());

        if(updateUserRequestDTO.password() != null && !updateUserRequestDTO.password().isBlank()){

            user.setPassword(passwordEncoder.encode(updateUserRequestDTO.password()));
        }

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    public void deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    public UserAuthResponseDTO findUserForAuth(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with email: " + email)
        );
        return userMapper.toAuthDTO(user);
    }

}
