package com.strike.strijkatelier.mapper;

import com.strike.strijkatelier.domain.entity.Role;
import com.strike.strijkatelier.domain.entity.UserEntity;
import com.strike.strijkatelier.domain.model.RegistrationRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@Component
public class UserRequestMapper {

    private PasswordEncoder passwordEncoder;

    public UserRequestMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity mapToUserEntity(RegistrationRequest registrationRequest, Role role){
       UserEntity userEntity = new UserEntity();
       userEntity.setFirstName(registrationRequest.getFirstName());
       userEntity.setLastName(registrationRequest.getLastName());
       userEntity.setEmail(registrationRequest.getEmail());
       userEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
       userEntity.setRoles(Collections.singletonList(role));
       return userEntity;
    }
}
