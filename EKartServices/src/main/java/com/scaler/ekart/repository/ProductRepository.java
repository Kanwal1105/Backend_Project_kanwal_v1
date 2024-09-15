package com.scaler.ekart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.scaler.ekart.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Product save(Product p);

    @Override
    void delete(Product entity);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Page<Product> findProductByTitle(String query, Pageable pageable);
}