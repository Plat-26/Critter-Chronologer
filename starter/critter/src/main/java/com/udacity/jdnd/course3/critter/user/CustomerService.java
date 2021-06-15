package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("This id is not mapped to a user")
        );
    }
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByPetId(long petId) {
        return customerRepository.findByPetsId(petId).orElseThrow(
                () -> new IllegalStateException("This pet id is not mapped to a customer")
        );
    }

}
