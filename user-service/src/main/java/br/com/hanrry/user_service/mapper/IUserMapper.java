package br.com.hanrry.user_service.mapper;

import br.com.hanrry.user_service.database.model.UserEntity;
import br.com.hanrry.user_service.dto.event.UserEventDTO;
import br.com.hanrry.user_service.dto.request.UserRequestDTO;
import br.com.hanrry.user_service.dto.response.UserAuthResponseDTO;
import br.com.hanrry.user_service.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserResponseDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserRequestDTO request);

    List<UserResponseDTO> toDTOList(List<UserEntity> users);

    UserEventDTO toEventDTO(UserEntity savedUser);

    UserAuthResponseDTO toAuthDTO(UserEntity user);
}
