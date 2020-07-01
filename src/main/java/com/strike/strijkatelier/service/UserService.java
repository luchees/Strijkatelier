package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Role;
import com.strike.strijkatelier.domain.model.RegistrationRequest;
import com.strike.strijkatelier.mapper.UserRequestMapper;
import com.strike.strijkatelier.repository.RoleRepository;
import com.strike.strijkatelier.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 1/7/2020 AD
 */

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRequestMapper mapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserRequestMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    public void registerUser(RegistrationRequest request){
        Role role = roleRepository.findByName("BASIC_USER");
        try{
            userRepository.save(mapper.mapToUserEntity(request, role));
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }


}
