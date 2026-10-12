package com.example.productmanager.service;

import com.example.productmanager.model.Product;
import com.example.productmanager.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public Product createProduct(Product product) {
        product.setId(null);
        return repository.save(product);
    }

    // READ ALL
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // READ ONE
    public Optional<Product> getProductById(String id) {
        return repository.findById(id);
    }

    // UPDATE
    public Optional<Product> updateProduct(
            String id, Product updatedProduct) {

        Optional<Product> result = repository.findById(id);

        if (result.isEmpty()) {
            return Optional.empty();
        }

        Product product = result.get();

        product.setName(updatedProduct.getName());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());

        return Optional.of(repository.save(product));
    }

    // DELETE
    public boolean deleteProduct(String id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}