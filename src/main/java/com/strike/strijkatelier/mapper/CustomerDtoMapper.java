package com.strike.strijkatelier.mapper;


import com.strike.strijkatelier.domain.entity.Customer;
import com.strike.strijkatelier.domain.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class CustomerDtoMapper {

    @Autowired
    private BasketDtoMapper mapper;

    public CustomerDtoMapper() {
    }

    public Customer mapToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setEmailAddress(customerDto.getEmailAddress());
        customer.setId(customerDto.getId());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setMinutesLeft(customerDto.getMinutesLeft());
       customer.setAccountNumber(customerDto.getAccountNumber());
        customer.setNote(customerDto.getNote());
        customer.setBaskets(customerDto.getBasketDtos().stream()
                .map(mapper::mapToBasket)
                .collect(Collectors.toList()));
        return customer;
    }

    public CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmailAddress(customer.getEmailAddress());
        customerDto.setId(customer.getId());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setNote(customer.getNote());
        customerDto.setAccountNumber(customer.getAccountNumber());
        customerDto.setBasketDtos(customer.getBaskets().stream()
                .map(mapper::mapToBasketDto)
                .collect(Collectors.toList()));
        return customerDto;
    }
}

