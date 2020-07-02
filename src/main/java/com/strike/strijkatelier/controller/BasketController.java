package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.domain.entity.Item;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private  BasketService basketService;

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Basket>> getActiveBaskets() {
        List<Basket> baskets = basketService.getActiveBaskets();
        return ResponseEntity.ok(baskets);
    }

    @GetMapping(value = "/active{startDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Basket>> getActiveBasketsByDate(@PathVariable Date startDate) {
        List<Basket> baskets = basketService.getActiveBasketsByDate(startDate);
        return ResponseEntity.ok(baskets);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Basket>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber){
            return ResponseEntity.ok(basketService.findAll());
    }

    @GetMapping(value = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Basket> findBasketById(@PathVariable long basketId) {
        try {
            Basket basket = basketService.findById(basketId);
            return ResponseEntity.ok(basket);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<Basket> addBasket(@Valid @RequestBody Basket basket)
            throws URISyntaxException {
        try {
            Basket newBasket = basketService.save(basket);
            return ResponseEntity.created(new URI("/api/baskets/" + newBasket.getId()))
                    .body(basket);
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

    @PutMapping(value = "/{basketId}")
    public ResponseEntity<Basket> updateBasket(@Valid @RequestBody Basket basket,
                                               @PathVariable long basketId) {
        try {
            basket.setId(basketId);
            basketService.update(basket);
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

    @PatchMapping("/{basketId}")
    public ResponseEntity<Void> updateAddress(@PathVariable long basketId,
                                              @RequestBody List<Item> items) {
        try {
            basketService.updateItems(basketId, items);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/{basketId}")
    public ResponseEntity<Void> deleteBasketById(@PathVariable long basketId) {
        try {
            basketService.deleteById(basketId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}