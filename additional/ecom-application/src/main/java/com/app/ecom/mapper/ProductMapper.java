package com.app.ecom.mapper;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;

public class ProductMapper {

    public static ProductResponse mapToProductResponse(Product product, ProductResponse productResponse) {
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategory(product.getCategory());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());
        return productResponse;
    }

    public static Product updateProductFromRequest(ProductRequest productRequest, Product product) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }
}
