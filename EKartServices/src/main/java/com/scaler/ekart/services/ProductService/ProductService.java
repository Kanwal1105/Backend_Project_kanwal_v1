package com.scaler.ekart.services.ProductService;


import com.scaler.ekart.dtos.products.SortParam;
import com.scaler.ekart.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    void delete(Long id);

    Optional<Product> findById(Long id);

    public Page<Product> searchProducts(String query, Integer pageNumber, Integer pageSize, List<SortParam> sortParams);
}
