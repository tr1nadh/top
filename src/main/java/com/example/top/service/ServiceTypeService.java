package com.example.top.service;

import com.example.top.entity.order.ServiceType;
import com.example.top.exception.UnknownIdException;
import com.example.top.repository.ServiceTypeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository repository;

    public void saveServiceType(ServiceType type) {
        if (type == null) throw new IllegalArgumentException("The object 'ServiceType' cannot be null");

        repository.save(type);

        log.info("Service type '" + type.getName() + "' is saved");
    }

    public List<ServiceType> findAllServiceTypes() {
        var serviceTypes = repository.findAll();

        log.info("Successfully retrieved all service types");
        return serviceTypes;
    }

    public ServiceType getServiceType(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        var optServiceType = repository.findById(id);
        if (optServiceType.isEmpty()) throw new UnknownIdException("No service type found with the id '" + id + "'");

        var serviceType = optServiceType.get();
        log.info("Service type '" + serviceType.getName() + "' has been retrieved");
        return serviceType;
    }

    public void deleteServiceType(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        var serviceType = getServiceType(id);
        if (serviceType == null) throw new IllegalStateException("Cannot delete the service type with the id '" + id + "' which doesn't exists");

        repository.deleteById(id);

        log.info("Service type '" + serviceType.getName() + "' has been deleted");
    }
}
