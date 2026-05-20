package br.com.hanrry.product_service.database.repository;

import br.com.hanrry.product_service.database.model.ProductEntity;
import br.com.hanrry.product_service.enums.Category;
import br.com.hanrry.product_service.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<ProductEntity, UUID> {

    List<ProductEntity> findByCategory(Category category);

    List<ProductEntity> findByProductStatus(ProductStatus status);

    boolean existsByName(String name);
}
