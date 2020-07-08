package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.domain.entity.Customer;
import com.strike.strijkatelier.domain.model.CustomerDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.exception.update.ErrorList;
import com.strike.strijkatelier.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/customers")
@Api(value = "customer-management", description = "Customer Management API", tags = "customer-management")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "Get all customers", nickname = "getAllCustomers", notes = "Gets all customers from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{Customers}"),
            @ApiResponse(code = 404, message = "No customers found in the database", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        try {
            return ResponseEntity.ok(customerService.findAll());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value = "Get a customer", nickname = "getACustomer", notes = "Gets a customer from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{Customer}"),
            @ApiResponse(code = 400, message = "No Customer was found", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable long customerId) {
        try {
            CustomerDto customerDto = customerService.findById(customerId);
            return ResponseEntity.ok(customerDto);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @ApiOperation(value = "add a customer", nickname = "addCustomer", notes = "add a customer to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer was successfully registered"),
            @ApiResponse(code = 409, message = "Customer already exists", response = ErrorList.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/")
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customerDto)
            throws URISyntaxException {
        try {
            CustomerDto newCustomerDto = customerService.save(customerDto);
            return ResponseEntity.ok(newCustomerDto);
        }  catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @ApiOperation(value = "update a customer", nickname = "updateCustomer", notes = "update customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer was successfully updated"),
            @ApiResponse(code = 404, message = "Customer was not found", response = ErrorList.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerDto customerDto,
                                                   @PathVariable long customerId) {
        try {
            customerDto.setId(customerId);
            customerService.update(customerDto);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @ApiOperation(value = "update baskets from a customer", nickname = "updateBasketsFromCustomer", notes = "update baskets of customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "baskets are successfully updated"),
            @ApiResponse(code = 404, message = "customer is not found", response = ErrorList.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PatchMapping("/{customerId}")
    public ResponseEntity<Void> updateBaskets(@PathVariable long customerId,
                                              @RequestBody List<Basket> baskets) {
        try {
            customerService.updateBaskets(customerId, baskets);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @ApiOperation(value = "updates a basket from a customer", nickname = "updateBasketFromCustomer", notes = "update a basket of a customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "basket is successfully updated"),
            @ApiResponse(code = 404, message = "customer is not found", response = ErrorList.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/basket/{basketId}")
    public ResponseEntity<Void> addBasket(@PathVariable long basketId,
                                          @RequestBody Basket basket) {
        try {
            customerService.addBasket(basketId, basket);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "delete customer", nickname = "deleteCustomer", notes = "deletes a customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "customer is successfully deleted"),
            @ApiResponse(code = 404, message = "customer was not found", response = ErrorList.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable long customerId) {
        try {
            customerService.deleteById(customerId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
