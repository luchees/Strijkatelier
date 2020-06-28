package com.strike.strijkatelier.persistence.dao;

import com.strike.strijkatelier.persistence.model.*;
import org.springframework.data.jpa.repository.*;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
