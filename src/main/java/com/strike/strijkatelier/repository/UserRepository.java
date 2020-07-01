package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    @Override
    void delete(UserEntity user);

}
