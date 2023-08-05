package com.example.top.service;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.order.Dimensions;
import com.example.top.repository.DimensionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DimensionsService {

    @Autowired
    private DimensionsRepository repository;

    public ResponseDto saveDimensions(Dimensions dimensions) {
        if (dimensions == null) throw new IllegalArgumentException("'dimensions' cannot be null");

        repository.save(dimensions);

        var message = (dimensions.getId() == null) ? "New dimensions has been saved" :
                "Dimensions has been renamed successfully";
        return ResponseDto.builder().success(true).message(message).build();
    }

    public ResponseDto findAllDimensions() {
        var dimensions = repository.findAll();

        var message = "Successfully retrieved all dimensions";
        return ResponseDto.builder().success(true).message(message).data(dimensions).build();
    }

    public ResponseDto findAllDimensions(int page) {
        var dimensions = repository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));

        var message = "Successfully retrieved dimensions of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(dimensions).build();
    }

    public ResponseDto deleteDimensions(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var OptDimensions = repository.findById(id);
        if (OptDimensions.isEmpty())
            throw new IllegalStateException("Cannot delete dimensions: No dimensions exists with the id '" + id + "'");

        repository.deleteById(id);

        var message = "Dimensions '" + OptDimensions.get().getName() + "' has been deleted";
        return ResponseDto.builder().success(true).message(message).build();
    }
}
