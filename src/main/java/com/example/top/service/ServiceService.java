package com.example.top.service;

import com.example.top.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ToString
@AllArgsConstructor
@Log
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    public boolean addService(com.example.top.entity.Service service) {
        if (service == null) {
            log.severe("Cannot add null as a service");
            return false;
        }

        repository.save(service);


        log.info("Service with the name '" + service.getName() + "' has been added" );
        return true;
    }
}
