package com.example.top.repository;

import com.example.top.entity.order.Dimensions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DimensionsRepository extends JpaRepository<Dimensions, Long> {
}
