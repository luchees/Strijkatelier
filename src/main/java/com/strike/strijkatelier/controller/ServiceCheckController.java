package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.domain.entity.ServiceCheck;
import com.strike.strijkatelier.domain.model.ServiceCheckDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.exception.update.ErrorList;
import com.strike.strijkatelier.service.ServiceCheckService;
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
@Api(value = "serviceCheck-management", description = "ServiceCheck Management API", tags = "serviceCheck-management")
public class ServiceCheckController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private ServiceCheckService serviceCheckService;

    @ApiOperation(value = "get all serviceChecks", nickname = "getAllServiceChecks", notes = "Gets all serviceChecks from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{getAllServiceChecks}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/serviceChecks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceCheckDto>> getAllServiceChecks(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
            return ResponseEntity.ok(serviceCheckService.findAll());
    }

    @ApiOperation(value = "get serviceCheck by id", nickname = "getServiceCheckById", notes = "Gets serviceCheck from database by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{serviceCheck}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/serviceChecks/{serviceCheckId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceCheck> findServiceCheckById(@PathVariable long serviceCheckId) {
        try {
            ServiceCheck book = serviceCheckService.findById(serviceCheckId);
            return ResponseEntity.ok(book);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @ApiOperation(value = "get serviceCheck by service check number", nickname = "getAllServiceChecksNumber", notes = "Gets serviceCheck from database by service check number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{serviceCheck}"),
            @ApiResponse(code = 404, message = "no serviceCheck found with name {serviceCheckName}"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/serviceCheck", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceCheckDto>> getAllServiceChecksByNumber(@RequestParam String serviceCheckNumber) {
        try{
            List<ServiceCheckDto> serviceCheck = serviceCheckService.findByServiceCheckNumber(serviceCheckNumber);
            return ResponseEntity.ok(serviceCheck);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


    }

    @ApiOperation(value = "add serviceCheck", nickname = "getServiceCheckByName", notes = "add serviceCheck to database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{serviceCheck}"),
            @ApiResponse(code = 409, message = "Resource Already exists"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/serviceChecks")
    public ResponseEntity<ServiceCheckDto> addServiceCheck(@Valid @RequestBody ServiceCheckDto serviceCheck)
            throws URISyntaxException {
        try {
            ServiceCheck newServiceCheck = serviceCheckService.save(serviceCheck);
            return ResponseEntity.created(new URI("/api/serviceChecks/" + newServiceCheck.getId())).body(serviceCheck);
        } catch (ResourceAlreadyExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @ApiOperation(value = "update serviceCheck", nickname = "updateServiceCheck", notes = "update serviceCheck in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{serviceCheck}"),
            @ApiResponse(code = 404, message = "ServiceCheck does not exist"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping(value = "/serviceChecks/{serviceCheckId}")
    public ResponseEntity<ServiceCheckDto> updateServiceCheck(@Valid @RequestBody ServiceCheckDto serviceCheck,
                                           @PathVariable long serviceCheckId) {
        try {
            serviceCheck.setId(serviceCheckId);
            serviceCheckService.update(serviceCheck);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @ApiOperation(value = "delete serviceCheck", nickname = "deleteServiceCheck", notes = "delete serviceCheck in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ServiceCheck is deleted"),
            @ApiResponse(code = 404, message = "ServiceCheck does not exist"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping(path = "/serviceChecks/{serviceCheckId}")
    public ResponseEntity deleteServiceCheckById(@PathVariable long serviceCheckId) {
        try {
            serviceCheckService.deleteById(serviceCheckId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}