package com.strike.strijkatelier.persistence.dao;

import com.strike.strijkatelier.persistence.model.*;
import org.springframework.data.jpa.repository.*;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
