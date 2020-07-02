package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    List<Basket> getBasketsByActive(Boolean active);
    List<Basket> getBasketsByActiveAndStartDateTime(Boolean active, Date startDateTime);
}