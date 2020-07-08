package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.domain.entity.Customer;
import com.strike.strijkatelier.domain.model.CustomerDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.mapper.CustomerDtoMapper;
import com.strike.strijkatelier.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private CustomerDtoMapper mapper;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    private boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    public CustomerDto findById(Long id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new ResourceNotFoundException("Cannot find Customer with id: " + id);
        } else return mapper.mapToCustomerDto(customer);
    }

    public Customer findCustomerById(Long id) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new ResourceNotFoundException("Cannot find Customer with id: " + id);
        } else return customer;
    }

    public List<CustomerDto> findAll() throws ResourceNotFoundException {
        List<CustomerDto> customerDtos = new ArrayList<>();

        customerRepository.findAll().forEach(customer ->
                customerDtos.add(mapper.mapToCustomerDto(customer))
        );
        if (customerDtos.isEmpty()) {
            throw new ResourceNotFoundException("No customers found in the database");
        }
        return customerDtos;
    }

    public CustomerDto save(CustomerDto customerDto) throws BadResourceException {
        try {
            Customer customer = new Customer();
            customer.setEmailAddress(customerDto.getEmailAddress());
            customer.setId(customerDto.getId());
            customer.setPhoneNumber(customerDto.getPhoneNumber());
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setMinutesLeft(customerDto.getMinutesLeft());
            customer.setNote(customerDto.getNote());
            customer.setBaskets(new ArrayList<>());

            return mapper.mapToCustomerDto(customerRepository.save(customer));
        } catch (Exception e) {
            BadResourceException exc = new BadResourceException("Failed to save item");
            throw exc;
        }
    }

    public void update(CustomerDto customerDto)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(customerDto.getId())) {
            if (!existsById(customerDto.getId())) {
                throw new ResourceNotFoundException("Cannot find Customer with id: " + customerDto.getId());
            }
            customerRepository.save(mapper.mapToCustomer(customerDto));
        } else {
            BadResourceException exc = new BadResourceException("Failed to save customer");
            exc.addErrorMessage("Customer is null or empty");
            throw exc;
        }
    }

    public void updateBaskets(Long id, List<Basket> baskets)
            throws ResourceNotFoundException {
        Customer customer = findCustomerById(id);
        customer.setBaskets(baskets);
        customerRepository.save(customer);
    }

    public void addBasket(Long id, Basket basket)
            throws ResourceNotFoundException {
        Customer customer = findCustomerById(id);
        customer.addBasket(basket);
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
