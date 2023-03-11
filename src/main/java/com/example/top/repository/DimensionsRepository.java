package com.example.top.repository;

import com.example.top.entity.Dimensions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimensionsRepository extends JpaRepository<Dimensions, Long> {
}
