package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.domain.model.RegistrationRequest;
import com.strike.strijkatelier.exception.update.ErrorList;
import com.strike.strijkatelier.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@Api(value = "user-management", description = "User Management API",tags = "user-management")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Register a User", nickname = "registerUser", notes = "Register a new system user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully registered"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/users/register", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationRequest request) {
        try {
            userService.registerUser(request);
        }
        catch ( Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
