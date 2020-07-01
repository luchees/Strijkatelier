package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.Privilege;
import org.springframework.data.jpa.repository.*;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
