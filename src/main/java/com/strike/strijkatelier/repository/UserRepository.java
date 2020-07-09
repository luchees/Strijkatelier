package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Override
    void delete(UserEntity user);

}
