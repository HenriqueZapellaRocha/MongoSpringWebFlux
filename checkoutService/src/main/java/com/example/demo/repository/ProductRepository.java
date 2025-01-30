package com.example.demo.repository;

import com.example.demo.domain.models.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {
}
