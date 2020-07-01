package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.exception.update.ErrorList;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @ApiOperation(value = "Register a User", nickname = "registerUser", notes = "Register a new system user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully registered"),
            @ApiResponse(code = 400, message = "Invalid request", response = ErrorList.class)

          })
    @PostMapping(value = "/users/register")
    public void registerUser(){

    }

}
