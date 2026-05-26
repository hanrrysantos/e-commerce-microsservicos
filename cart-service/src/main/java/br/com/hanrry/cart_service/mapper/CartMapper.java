package br.com.hanrry.cart_service.mapper;

import br.com.hanrry.cart_service.database.model.CartEntity;
import br.com.hanrry.cart_service.database.model.CartItemEntity;
import br.com.hanrry.cart_service.dto.response.CartItemResponseDTO;
import br.com.hanrry.cart_service.dto.response.CartResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponseDTO toDTO(CartEntity entity);

    CartItemResponseDTO toItemDTO(CartItemEntity entity);

    List<CartItemResponseDTO> toItemDTOList(List<CartItemEntity> entities);
}
