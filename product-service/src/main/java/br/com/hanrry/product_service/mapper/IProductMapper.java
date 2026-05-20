package br.com.hanrry.product_service.mapper;

import br.com.hanrry.product_service.database.model.ProductEntity;
import br.com.hanrry.product_service.dto.request.ProductRequestDTO;
import br.com.hanrry.product_service.dto.response.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    ProductResponseDTO toDTO(ProductEntity entity);

    List<ProductResponseDTO> toDTOList(List<ProductEntity> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "productStatus", ignore = true)
    ProductEntity toEntity(ProductRequestDTO request);
}
