package br.com.hanrry.product_service.service;

import br.com.hanrry.product_service.database.model.ProductEntity;
import br.com.hanrry.product_service.database.repository.IProductRepository;
import br.com.hanrry.product_service.dto.request.ProductRequestDTO;
import br.com.hanrry.product_service.dto.request.UpdatePriceRequestDTO;
import br.com.hanrry.product_service.dto.request.UpdateStockRequestDTO;
import br.com.hanrry.product_service.dto.response.ProductResponseDTO;
import br.com.hanrry.product_service.enums.Category;
import br.com.hanrry.product_service.enums.ProductStatus;
import br.com.hanrry.product_service.exception.ProductAlreadyExistsException;
import br.com.hanrry.product_service.exception.ProductNotFoundException;
import br.com.hanrry.product_service.mapper.IProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;
    private final IProductMapper productMapper;

    public ProductResponseDTO createProduct(ProductRequestDTO request){

        if(productRepository.existsByName(request.name())){
            throw new ProductAlreadyExistsException("Product already exists with this name: " + request.name());
        }

        ProductEntity product = productMapper.toEntity(request);

        ProductEntity savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public ProductResponseDTO updatePrice(UUID id, UpdatePriceRequestDTO request){
        ProductEntity product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with this id: " + id)
        );

        product.setPrice(request.price());

        ProductEntity savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public ProductResponseDTO updateStock(UUID id, UpdateStockRequestDTO request){
        ProductEntity product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with this id: " + id)
        );

        product.setStockQuantity(request.stockQuantity());

        ProductEntity savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    public ProductResponseDTO findById(UUID id){
        ProductEntity product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with this id: " + id)
        );


        return productMapper.toDTO(product);
    }

    public List<ProductResponseDTO> findByCategory(Category category){
        List<ProductEntity> product = productRepository.findByCategory(category);

        return productMapper.toDTOList(product);
    }

    public List<ProductResponseDTO> findByProductStatus(ProductStatus status){
        List<ProductEntity> product = productRepository.findByProductStatus(status);

        return productMapper.toDTOList(product);
    }

    public List<ProductResponseDTO> findAll(){
        List<ProductEntity> product = productRepository.findAll();

        return productMapper.toDTOList(product);
    }

    public ProductResponseDTO update(UUID id, ProductRequestDTO request) {
        ProductEntity product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with this id: " + id)
        );

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setCategory(request.category());

        return productMapper.toDTO(productRepository.save(product));
    }

    public void deactivate(UUID id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with this id: " + id)
        );

        product.setProductStatus(ProductStatus.INACTIVE);
        productRepository.save(product);
    }
}
