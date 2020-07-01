package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.User;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

}
