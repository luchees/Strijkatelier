package com.strike.strijkatelier.persistence.dao;

import com.strike.strijkatelier.persistence.model.*;
import org.springframework.data.jpa.repository.*;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {

    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(User userLocation);

}
