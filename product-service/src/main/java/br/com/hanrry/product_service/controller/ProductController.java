package br.com.hanrry.product_service.controller;

import br.com.hanrry.product_service.dto.request.ProductRequestDTO;
import br.com.hanrry.product_service.dto.request.UpdatePriceRequestDTO;
import br.com.hanrry.product_service.dto.request.UpdateStockRequestDTO;
import br.com.hanrry.product_service.dto.response.ProductResponseDTO;
import br.com.hanrry.product_service.enums.Category;
import br.com.hanrry.product_service.enums.ProductStatus;
import br.com.hanrry.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO request){
        ProductResponseDTO product = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PatchMapping(value = "/{id}/price")
    public ResponseEntity<ProductResponseDTO> updateProductPrice(
            @RequestBody UpdatePriceRequestDTO request, @PathVariable UUID id){
        ProductResponseDTO product = productService.updatePrice(id, request);

        return ResponseEntity.ok().body(product);
    }

    @PatchMapping(value = "/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateProductStock(
            @RequestBody UpdateStockRequestDTO request, @PathVariable UUID id){
        ProductResponseDTO product = productService.updateStock(id, request);

        return ResponseEntity.ok().body(product);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(
            @PathVariable UUID id){
        ProductResponseDTO product = productService.findById(id);

        return ResponseEntity.ok().body(product);
    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<ProductResponseDTO>> findProductsByCategory(
            @RequestParam Category category){
        List<ProductResponseDTO> products = productService.findByCategory(category);

        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<ProductResponseDTO>> findProductByStatus(
            @RequestParam ProductStatus status){
        List<ProductResponseDTO> products = productService.findByProductStatus(status);

        return ResponseEntity.ok().body(products);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> findAll(){
        List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @RequestBody ProductRequestDTO request, @PathVariable UUID id){
        ProductResponseDTO product = productService.update(id, request);

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deactivateProduct(
            @PathVariable UUID id){
        productService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
