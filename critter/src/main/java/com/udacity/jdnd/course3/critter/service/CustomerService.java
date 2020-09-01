package com.udacity.jdnd.course3.critter.service;

import java.util.List;

import com.udacity.jdnd.course3.critter.data.user.Customer;
import com.udacity.jdnd.course3.critter.data.user.CustomerRepository;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Long save(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public Customer get(Long id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getByPetId(Long id) {
        return customerRepository.findByPetsId(id);
    }

}