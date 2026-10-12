package com.example.productmanager.repository;

import com.example.productmanager.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository
        extends MongoRepository<Product, String> {
}