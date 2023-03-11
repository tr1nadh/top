package com.example.top.service;

import com.example.top.entity.Customer;
import com.example.top.repository.CustomerRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public boolean addCustomer(Customer customer) {
        if (customer == null) {
            log.severe("Cannot add null as a customer");
            return false;
        }

        repository.save(customer);

        log.info("Customer with the name '" + customer.getName() + "' has been added");
        return true;
    }

    public Customer getCustomer(Long id) {
        var customer = repository.getReferenceById(id);

        log.info("Customer with the name '" + customer.getName() + "' has been retrieved");
        return customer;
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);

        log.info("Customer with the id '" + id + "' has been deleted");
    }
}
