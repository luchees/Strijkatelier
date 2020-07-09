package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Role;
import com.strike.strijkatelier.domain.entity.UserEntity;
import com.strike.strijkatelier.domain.model.LoginRequest;
import com.strike.strijkatelier.domain.model.LoginResponse;
import com.strike.strijkatelier.domain.model.RegistrationRequest;
import com.strike.strijkatelier.mapper.UserRequestMapper;
import com.strike.strijkatelier.repository.RoleRepository;
import com.strike.strijkatelier.repository.UserRepository;
import com.strike.strijkatelier.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 8/7/2020 AD
 */

@Service
public class CredentialService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtTokenUtil;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SecurityService securityService;
    private UserRequestMapper mapper;

    public CredentialService(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, UserRepository userRepository, RoleRepository roleRepository, SecurityService securityService, UserRequestMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
        this.mapper = mapper;
    }

    public void registerUser(RegistrationRequest request) throws Exception {
        Role role = roleRepository.findByName("BASIC_USER");
        try {
            userRepository.save(mapper.mapToUserEntity(request, role));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) throws Exception {
        authenticate(request.getEmail(), request.getPassword());

        final UserDetails userDetails = securityService.loadUserByUsername(request.getEmail());

        return ResponseEntity.ok(new LoginResponse(jwtTokenUtil.generateToken(userDetails)));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
    }
}

