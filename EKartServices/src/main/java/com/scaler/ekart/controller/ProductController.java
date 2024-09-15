package com.scaler.ekart.controller;

import com.scaler.ekart.dtos.products.*;
import com.scaler.ekart.models.Product;
import com.scaler.ekart.services.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    private RedisTemplate<String , Object> redisTemplate;

    public ProductController(@Qualifier("dbProductService") ProductService productService, RedisTemplate<String, Object> redisTemplate) {
        this.productService = productService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/addProduct")
    public CreateProductResponseDto addNewProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        Product product = productService.createProduct(
                createProductRequestDto.toProduct()
        );

        return CreateProductResponseDto.fromProduct(
                product
        );
    }

    @GetMapping("/getProducts")
    public GetAllProductsResponseDto fetchAllProducts() {
        List<Product> products = productService.getAllProducts();
        GetAllProductsResponseDto response = new GetAllProductsResponseDto();

        List<GetProductDto> getProductResponseDtos = new ArrayList<>();

        for (Product product : products) {
            getProductResponseDtos.add(GetProductDto.from(product));
        }

        response.setProducts(getProductResponseDtos);

        return response;
    }

    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id) {
        Product prod = null;
        prod = (Product) redisTemplate.opsForHash().get("_PRODUCTS", id);

        if(prod != null){
            System.out.println("Found in cache");
            return prod;
        }
        else {
            System.out.println("Not Found in cache");
            Optional<Product> data = productService.findById(id);
            redisTemplate.opsForHash().put("_PRODUCTS", id , data.get());
            return productService.findById(id).get();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @PostMapping("/search")
    public Page<Product> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {
        return productService.searchProducts(searchRequestDto.getQuery(), searchRequestDto.getPageNumber(),
                searchRequestDto.getPageLimit(), searchRequestDto.getSortParamList());
    }
}