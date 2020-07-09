package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Role;
import com.strike.strijkatelier.domain.entity.UserEntity;
import com.strike.strijkatelier.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Simon Cek (simon.cek@integrationworks.co.th)
 * @created 9/7/2020 AD
 */

@Service
public class SecurityService implements UserDetailsService {

    private UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOpt = userRepository.findByEmail(username);

        if (userEntityOpt.isEmpty()) {
            throw new UsernameNotFoundException("Not found!");
        }

        UserEntity user = userEntityOpt.get();
        return new User(user.getEmail(), user.getPassword(), getAuthority(user.getRoles()));
    }

    private Set<SimpleGrantedAuthority> getAuthority(Collection<Role> roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return authorities;
    }

}
