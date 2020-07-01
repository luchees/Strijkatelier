package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.UserEntity;
import com.strike.strijkatelier.domain.model.RegistrationRequest;
import com.strike.strijkatelier.mapper.UserRequestMapper;
import com.strike.strijkatelier.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRequestMapper mapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRequestMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void registerUser(RegistrationRequest request){
        userRepository.save(mapper.mapToUserEntity(request));
    }


}
