package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.Exceptions.BadResourceException;
import com.strike.strijkatelier.Exceptions.ResourceAlreadyExistsException;
import com.strike.strijkatelier.Exceptions.ResourceNotFoundException;
import com.strike.strijkatelier.model.Item;
import com.strike.strijkatelier.model.Item;
import com.strike.strijkatelier.service.ItemService;
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
@RequestMapping("/api")
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private  ItemService itemService;


    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) Long id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseEntity.ok(itemService.findAll());
        }
        else {
            return ResponseEntity.ok(itemService.findAll());
        }
    }

    @GetMapping(value = "/items/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> findItemById(@PathVariable long itemId) {
        try {
            Item book = itemService.findById(itemId);
            return ResponseEntity.ok(book);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/items")
    public ResponseEntity<Item> addItem(@Valid @RequestBody Item item)
            throws URISyntaxException {
        try {
            Item newItem = itemService.save(item);
            return ResponseEntity.created(new URI("/api/items/" + newItem.getId()))
                    .body(item);
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

    @PutMapping(value = "/items/{itemId}")
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item,
                                                 @PathVariable long itemId) {
        try {
            item.setId(itemId);
            itemService.update(item);
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

    @PatchMapping("/item/update")
    public ResponseEntity<Void> updateItem(@Valid @RequestBody Item item) {
        try {
            itemService.update(item);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException | BadResourceException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/item/{itemId}/price")
    public ResponseEntity<Void> updatePrice(@Valid @RequestBody double price, @PathVariable long itemId) {
        try {
            itemService.updatePrice(itemId, price);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/item/{itemId}/minutes")
    public ResponseEntity<Void> updateMinutes(@Valid @RequestBody int minutes, @PathVariable long itemId) {
        try {
            itemService.updateMinutes(itemId, minutes);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<Void> updateAddress(@RequestBody Item item) {
        try {
            itemService.update(item);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException | BadResourceException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/items/{itemId}")
    public ResponseEntity<Void> deleteItemById(@PathVariable long itemId) {
        try {
            itemService.deleteById(itemId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}