package com.example.top.repository;

import com.example.top.entity.order.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
}
