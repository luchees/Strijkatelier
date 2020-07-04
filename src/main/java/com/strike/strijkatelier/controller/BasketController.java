package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.domain.model.BasketDto;
import com.strike.strijkatelier.domain.model.ItemDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.exception.update.ErrorList;
import com.strike.strijkatelier.service.BasketService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/baskets")
@Api(value = "basket-management", description = "Basket Management API", tags = "basket-management")
public class BasketController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private BasketService basketService;

    @ApiOperation(value = "get active baskets", nickname = "getActiveBaskets", notes = "Gets all active baskets from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{baskets}"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasketDto>> getActiveBaskets() {
        List<BasketDto> basketDtos = basketService.getActiveBaskets();
        return ResponseEntity.ok(basketDtos);
    }

    @ApiOperation(value = "get active baskets by startDate", nickname = "getActiveBasketsByStartDate", notes = "Gets all active baskets by startdate from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{baskets}"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/active{startDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasketDto>> getActiveBasketsByDate(@PathVariable Date startDate) {
        List<BasketDto> baskets = basketService.getActiveBasketsByDate(startDate);
        return ResponseEntity.ok(baskets);
    }

    @ApiOperation(value = "get all baskets ", nickname = "getBaskets", notes = "Gets all baskets from database ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{baskets}"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasketDto>> findAll(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok(basketService.findAll());
    }

    @ApiOperation(value = "get basket by id ", nickname = "getBasketsById", notes = "Gets baskets by id from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{basket}"),
            @ApiResponse(code = 404, message = "no basket found with name {itemName}"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketDto> getBasketsById(@PathVariable long basketId) {
        try {
            BasketDto basketDto = basketService.findById(basketId);
            return ResponseEntity.ok(basketDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @ApiOperation(value = "adds basket", nickname = "addBasket", notes = "adds a basket to the database ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{basket}"),
            @ApiResponse(code = 409, message = "basket already exists {basketId}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/")
    public ResponseEntity<BasketDto> addBasket(@Valid @RequestBody BasketDto basketDto)
            throws URISyntaxException {
        try {
            BasketDto newBasket = basketService.save(basketDto);
            return ResponseEntity.created(new URI("/api/baskets/" + newBasket.getId()))
                    .body(basketDto);
        } catch (ResourceAlreadyExistsException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "updates basket", nickname = "updateBasket", notes = "updates a basket to the database ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{basket}"),
            @ApiResponse(code = 404, message = "basket doesnt exists {basketId}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping(value = "/{basketId}")
    public ResponseEntity<BasketDto> updateBasket(@Valid @RequestBody BasketDto basketDto,
                                                  @PathVariable long basketId) {
        try {
            basketDto.setId(basketId);
            basketService.update(basketDto);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (BadResourceException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @ApiOperation(value = "set items in basket", nickname = "setItems", notes = "sets the items of a basket to the database ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{basket}"),
            @ApiResponse(code = 404, message = "basket doesnt exists {basketId}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PatchMapping("/{basketId}")
    public ResponseEntity<Void> setItems(@PathVariable long basketId,
                                              @RequestBody List<ItemDto> itemDtos) {
        try {
            basketService.updateItems(basketId, itemDtos);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (BadResourceException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @ApiOperation(value = "delete basket", nickname = "deleteBasket", notes = "delets a basket to the database ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{basket}"),
            @ApiResponse(code = 404, message = "basket doesnt exists {basketId}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping(path = "/{basketId}")
    public ResponseEntity<Void> deleteBasketById(@PathVariable long basketId) {
        try {
            basketService.deleteById(basketId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "adds item in basket", nickname = "addItem", notes = "adds  item of a basket to the database ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{basket}"),
            @ApiResponse(code = 404, message = "basket doesnt exists {basketId}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/{basketId}/items")
    public ResponseEntity<BasketDto> addItemToBasket(@PathVariable long basketId, @Valid @RequestBody ItemDto itemDto)
            throws URISyntaxException {
        try {
            BasketDto basketDto = basketService.findById(basketId);
            List<ItemDto> itemDtos = new ArrayList<>();
            itemDtos.add(itemDto);
            BasketDto newBasket = basketService.updateItems(basketId, itemDtos);
            return ResponseEntity.created(new URI("/api/baskets/" + newBasket.getId()))
                    .body(basketDto);
        } catch (BadResourceException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "calculates price of the basket", nickname = "calculatePriceBasket", notes = "calculates the price of a basket and saves to the database ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{basket}"),
            @ApiResponse(code = 404, message = "basket doesnt exists {basketId}"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/{basketId}/calculatePrice")
    public ResponseEntity<BasketDto> calculatePriceOfBasket(@PathVariable long basketId)
            throws URISyntaxException {
        try {
            BasketDto basketDto = basketService.findById(basketId);

            return ResponseEntity.ok().body(  basketService.calculatePrice(basketDto));
        }  catch (ResourceNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}