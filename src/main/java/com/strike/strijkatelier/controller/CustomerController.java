package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.domain.entity.Customer;
import com.strike.strijkatelier.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private CustomerService customerService;


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) Long id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseEntity.ok(customerService.findAll());
        }
        else {
            return ResponseEntity.ok(customerService.findAll());
        }
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findCustomerById(@PathVariable long customerId) {
        try {
            Customer book = customerService.findById(customerId);
            return ResponseEntity.ok(book);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer)
            throws URISyntaxException {
        try {
            Customer newCustomer = customerService.save(customer);
            return ResponseEntity.created(new URI("/api/customer/" + newCustomer.getId()))
                    .body(customer);
        } catch (ResourceAlreadyExistsException ex) {
            // log exception first, then return Conflict (409)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer,
                                               @PathVariable long customerId) {
        try {
            customer.setId(customerId);
            customerService.update(customer);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Void> updateBaskets(@PathVariable long customerId,
                                              @RequestBody List<Basket> baskets) {
        try {
            customerService.updateBaskets(customerId, baskets);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/basket/{basketId}")
    public ResponseEntity<Void> addBasket(@PathVariable long basketId,
                                              @RequestBody Basket basket) {
        try {
            customerService.addBasket(basketId, basket);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/{customerId}")
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
