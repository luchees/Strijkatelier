package com.strike.strijkatelier.service;

import com.strike.strijkatelier.exceptions.BadResourceException;
import com.strike.strijkatelier.exceptions.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exceptions.ResourceNotFoundException;
import com.strike.strijkatelier.model.Bucket;
import com.strike.strijkatelier.model.Customer;
import com.strike.strijkatelier.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    private boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    public Customer findById(Long id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new ResourceNotFoundException("Cannot find Customer with id: " + id);
        } else return customer;
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    public Customer save(Customer customer) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(customer.getId())) {
            if (customer.getId() != null && existsById(customer.getId())) {
                throw new ResourceAlreadyExistsException("Customer with id: " + customer.getId() +
                        " already exists");
            }
            return customerRepository.save(customer);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save customer");
            exc.addErrorMessage("Customer is null or empty");
            throw exc;
        }
    }

    public void update(Customer customer)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(customer.getId())) {
            if (!existsById(customer.getId())) {
                throw new ResourceNotFoundException("Cannot find Customer with id: " + customer.getId());
            }
            customerRepository.save(customer);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save customer");
            exc.addErrorMessage("Customer is null or empty");
            throw exc;
        }
    }

    public void updateBuckets(Long id, List<Bucket> buckets)
            throws ResourceNotFoundException {
        Customer customer = findById(id);
        customer.setBuckets(buckets);
        customerRepository.save(customer);
    }

    public void addBucket(Long id, Bucket bucket)
            throws ResourceNotFoundException {
        Customer customer = findById(id);
        customer.addBucket(bucket);
        customerRepository.save(customer);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find customer with id: " + id);
        } else {
            customerRepository.deleteById(id);
        }
    }

    public Long count() {
        return customerRepository.count();
    }
}
