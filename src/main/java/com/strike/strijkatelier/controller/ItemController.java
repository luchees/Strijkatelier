package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.domain.entity.Item;
import com.strike.strijkatelier.domain.model.ItemDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.exception.update.ErrorList;
import com.strike.strijkatelier.service.ItemService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@Api(value = "item-management", description = "Item Management API", tags = "item-management")
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "get all items", nickname = "getAllItems", notes = "Gets all items from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{getAllItems}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDto>> getAllItems(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
            return ResponseEntity.ok(itemService.findAll());
    }

    @ApiOperation(value = "get item by id", nickname = "getItemById", notes = "Gets item from database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{item}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/items/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> findItemById(@PathVariable long itemId) {
        try {
            Item item = itemService.findById(itemId);
            return ResponseEntity.ok(item);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @ApiOperation(value = "get item by name", nickname = "getAllItemsByName", notes = "Gets item from database by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{item}"),
            @ApiResponse(code = 404, message = "no item found with name {itemName}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDto>> getAllItemsByName(@RequestParam String itemName) {
        try{
            List<ItemDto> item = itemService.findByName(itemName);
            return ResponseEntity.ok(item);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


    }

    @ApiOperation(value = "add item", nickname = "getItemByName", notes = "add item to database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{item}"),
            @ApiResponse(code = 409, message = "Resource Already exists"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/items")
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto item)
            throws URISyntaxException {
        try {
            Item newItem = itemService.save(item);
            return ResponseEntity.created(new URI("/api/items/" + newItem.getId())).body(item);
        } catch (ResourceAlreadyExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @ApiOperation(value = "update item", nickname = "updateItem", notes = "update item in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{item}"),
            @ApiResponse(code = 404, message = "Item does not exist"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping(value = "/items/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@Valid @RequestBody ItemDto item,
                                           @PathVariable long itemId) {
        try {
            System.out.println(itemId+"" + item.getItemName());
            item.setId(itemId);
            itemService.update(item);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "update price of item", nickname = "updatePrice", notes = "update price of item in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Price is updated"),
            @ApiResponse(code = 404, message = "Item does not exist"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PatchMapping("/item/{itemId}/price")
    public ResponseEntity updatePrice(@Valid @RequestBody double price, @PathVariable long itemId) {
        try {
            itemService.updatePrice(itemId, price);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "update minutes of item", nickname = "updateMinute", notes = "update minutes of item in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Minutes are updated"),
            @ApiResponse(code = 404, message = "Item does not exist"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PatchMapping("/item/{itemId}/minutes")
    public ResponseEntity updateMinutes(@Valid @RequestBody int minutes, @PathVariable long itemId) {
        try {
            itemService.updateMinutes(itemId, minutes);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @ApiOperation(value = "delete item", nickname = "deleteItem", notes = "delete item in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item is deleted"),
            @ApiResponse(code = 404, message = "Item does not exist"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping(path = "/items/{itemId}")
    public ResponseEntity deleteItemById(@PathVariable long itemId) {
        try {
            itemService.deleteById(itemId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}