package com.example.top.service;

import com.example.top.entity.order.Dimensions;
import com.example.top.repository.DimensionsRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class DimensionsService {

    @Autowired
    private DimensionsRepository repository;

    public void saveDimensions(Dimensions dimensions) {
        if (dimensions == null) throw new IllegalArgumentException("'dimensions' cannot be null");

        repository.save(dimensions);

        log.info("Dimensions '" + dimensions.getName() + "' is saved");
    }

    public List<Dimensions> findAllDimensions() {
        var dimensions = repository.findAll();

        log.info("Successfully retrieved all dimensions");
        return dimensions;
    }

    public Dimensions getDimensions(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optDimensions = repository.findById(id);
        if (optDimensions.isEmpty()) {
            log.severe("No dimensions found with the id '" + id + "'");
            return null;
        }

        var dimensions = optDimensions.get();
        log.info("Dimensions '" + dimensions.getName() + "' has been retrieved");
        return dimensions;
    }

    public void deleteDimensions(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var dimensions = getDimensions(id);
        if (dimensions == null)
            throw new IllegalStateException("Cannot delete dimensions: No dimensions exists with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Dimensions '" + dimensions.getName() + "' has been deleted");
    }
}
