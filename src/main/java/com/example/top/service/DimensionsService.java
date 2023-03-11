package com.example.top.service;

import com.example.top.entity.Dimensions;
import com.example.top.repository.DimensionsRepository;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ToString
@AllArgsConstructor
@Log
public class DimensionsService {

    @Autowired
    private DimensionsRepository repository;

    public boolean addDimensions(Dimensions dimensions) {
        if (dimensions == null) {
            log.severe("Cannot add null as an dimensions");
            return false;
        }

        repository.save(dimensions);

        log.info(dimensions + " has been added");
        return true;
    }
}
