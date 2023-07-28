package com.example.top.service;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.order.Dimensions;
import com.example.top.repository.DimensionsRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class DimensionsService {

    @Autowired
    private DimensionsRepository repository;

    public ResponseDto saveDimensions(Dimensions dimensions) {
        if (dimensions == null) throw new IllegalArgumentException("'dimensions' cannot be null");

        repository.save(dimensions);

        var message = (dimensions.getId() == null) ? "New dimensions has been saved" :
                "Dimensions has been renamed successfully";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    public List<Dimensions> findAllDimensions() {
        var dimensions = repository.findAll();

        log.info("Successfully retrieved all dimensions");
        return dimensions;
    }

    public List<Dimensions> findAllDimensions(int page) {
        var dimensions = repository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));

        log.info("Successfully retrieved all dimensions");
        return dimensions.get().toList();
    }

    public ResponseDto deleteDimensions(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var OptDimensions = repository.findById(id);
        if (OptDimensions.isEmpty())
            throw new IllegalStateException("Cannot delete dimensions: No dimensions exists with the id '" + id + "'");

        repository.deleteById(id);

        var message = "Dimensions '" + OptDimensions.get().getName() + "' has been deleted";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }
}
