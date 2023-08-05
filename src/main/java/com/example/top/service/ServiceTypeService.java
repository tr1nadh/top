package com.example.top.service;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.order.ServiceType;
import com.example.top.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository repository;

    public ResponseDto saveServiceType(ServiceType type) {
        if (type == null) throw new IllegalArgumentException("'type' cannot be null");

        repository.save(type);

        var message = (type.getId() == null) ? "New service type '"+ type.getName() +"' has been saved" :
                "Service type has been renamed successfully";
        return ResponseDto.builder().success(true).message(message).build();
    }

    public ResponseDto findAllServiceTypes() {
        var serviceTypes = repository.findAll();

        var message = "Successfully retrieved all service types";
        return ResponseDto.builder().success(true).message(message).data(serviceTypes).build();
    }

    public ResponseDto findAllServiceTypes(int page) {
        var serviceTypes = repository.findAll(
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));

        var message = "Successfully retrieved all service types";
        return ResponseDto.builder().success(true).message(message).data(serviceTypes).build();
    }

    public ResponseDto deleteServiceType(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var OptServiceType = repository.findById(id);
        if (OptServiceType.isEmpty())
            throw new IllegalStateException("Cannot delete the service type: No service type exists with the id '" + id + "'");

        repository.deleteById(id);

        var message = "Service type '" + OptServiceType.get().getName() + "' has been deleted";
        return ResponseDto.builder().success(true).message(message).build();
    }
}
