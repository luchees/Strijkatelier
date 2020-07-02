package com.strike.strijkatelier.mapper;


import com.strike.strijkatelier.domain.entity.Customer;
import com.strike.strijkatelier.domain.model.CustomerDto;
import org.springframework.stereotype.Component;


@Component
public class CustomerDtoMapper {

    public CustomerDtoMapper() {
    }

    public Customer mapToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setEmailaddress(customerDto.getEmailaddress());
        customer.setId(customerDto.getId());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setMinutesLeft(customerDto.getMinutesLeft());
        customer.setBaskets(customerDto.getBaskets());
        return customer;
    }

    public CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmailAddress(customer.getEmailaddress());
        customerDto.setId(customer.getId());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setBaskets(customer.getBaskets());
        return customerDto;
    }
}

