package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @PostConstruct
    public void initData() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("Description for product 1");
        product1.setPrice(10.0);
        saveProduct(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("Description for product 2");
        product2.setPrice(20.0);
        saveProduct(product2);
    }
}

