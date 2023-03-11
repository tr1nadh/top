package com.example.top.service;

import com.example.top.entity.EmployeeBill;
import com.example.top.repository.EmployeeBillRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class EmployeeBillService {

    @Autowired
    private EmployeeBillRepository repository;

    public boolean addService(EmployeeBill service) {
        if (service == null) {
            log.severe("Cannot add null as a service");
            return false;
        }

        repository.save(service);

        log.info("Service has been added");
        return true;
    }
}
