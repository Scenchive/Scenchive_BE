package com.example.scenchive.domain.filter.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByBrandName(String brandName);

    List<Brand> findByBrandNameContainingIgnoreCase(String brandname);
}
