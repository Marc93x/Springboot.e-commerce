package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        logger.info("Récupération de tous les produits");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        logger.info("Récupération du produit avec l'ID: {}", id);
        return productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'ID: " + id));
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        logger.info("Ajout d'un produit: {}", product);
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        logger.info("Suppression du produit avec l'ID: {}", id);
        productService.deleteProduct(id);
    }
}

