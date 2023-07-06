package com.example.top.service;

import com.example.top.entity.order.ServiceType;
import com.example.top.repository.ServiceTypeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository repository;

    public void saveServiceType(ServiceType type) {
        if (type == null) throw new IllegalArgumentException("'type' cannot be null");

        repository.save(type);

        log.info("Service type '" + type.getName() + "' is saved");
    }

    public List<ServiceType> findAllServiceTypes() {
        var serviceTypes = repository.findAll();

        log.info("Successfully retrieved all service types");
        return serviceTypes;
    }

    public List<ServiceType> findAllServiceTypes(int page) {
        var serviceTypes = repository.findAll(
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));

        log.info("Successfully retrieved all service types");
        return serviceTypes.get().toList();
    }

    public ServiceType getServiceType(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optServiceType = repository.findById(id);
        if (optServiceType.isEmpty()) {
            log.severe("No service type found with the id '" + id + "'");
            return null;
        }

        var serviceType = optServiceType.get();
        log.info("Service type '" + serviceType.getName() + "' has been retrieved");
        return serviceType;
    }

    public ServiceType deleteServiceType(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var serviceType = repository.findById(id);
        if (serviceType.isEmpty())
            throw new IllegalStateException("Cannot delete the service type: No service type exists with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Service type '" + serviceType.get().getName() + "' has been deleted");
        return serviceType.get();
    }
}
