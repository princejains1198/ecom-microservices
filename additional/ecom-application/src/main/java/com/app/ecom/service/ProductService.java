package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.mapper.ProductMapper;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        // Implementation goes here
        Product product = ProductMapper.updateProductFromRequest(productRequest, new Product());
        Product saveProduct = productRepository.save(product);
        return ProductMapper.mapToProductResponse(saveProduct, new ProductResponse());
    }

    public List<ProductResponse> getAllProducts() {
        // Implementation goes here
        return productRepository.findByActiveTrue().stream()
                .map(product -> ProductMapper.mapToProductResponse(product, new ProductResponse()))
                .toList();
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest updateProductRequest) {
        // Implementation goes here
        return productRepository.findById(id)
                .map(exitingProduct -> {
                    ProductMapper.updateProductFromRequest(updateProductRequest, exitingProduct);
                    Product saveProduct = productRepository.save(exitingProduct);
                    return ProductMapper.mapToProductResponse(saveProduct, new ProductResponse());
                });
    }


    public boolean deleteProduct(Long id) {
        // Implementation goes here
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        // Implementation goes here
        return productRepository.searchProducts(keyword).stream()
                .map(product -> ProductMapper.mapToProductResponse(product, new ProductResponse()))
                .toList();
    }
}

