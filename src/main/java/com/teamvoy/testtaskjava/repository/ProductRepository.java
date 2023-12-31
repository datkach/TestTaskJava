package com.teamvoy.testtaskjava.repository;

import com.teamvoy.testtaskjava.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByName(String name);
}
