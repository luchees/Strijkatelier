package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.model.RegistrationRequest;
import com.strike.strijkatelier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegistrationRequest request){

    }


}
