package com.strike.strijkatelier.persistence.dao;

import com.strike.strijkatelier.persistence.model.*;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

}
