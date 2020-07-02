package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.domain.entity.Item;
import com.strike.strijkatelier.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;


    private boolean existsById(Long id) {
        return basketRepository.existsById(id);
    }

    public Basket findById(Long id) throws ResourceNotFoundException {
        Basket basket = basketRepository.findById(id).orElse(null);
        if (basket == null) {
            throw new ResourceNotFoundException("Cannot find Basket with id: " + id);
        } else return basket;
    }

    public List<Basket> findAll() {
        List<Basket> baskets = new ArrayList<>();
        basketRepository.findAll().forEach(baskets::add);
        return baskets;
    }


    public Basket save(Basket basket) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(basket.getId())) {
            if (basket.getId() != null && existsById(basket.getId())) {
                throw new ResourceAlreadyExistsException("Basket with id: " + basket.getId() +
                        " already exists");
            }
            return basketRepository.save(basket);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save basket");
            exc.addErrorMessage("Basket is null or empty");
            throw exc;
        }
    }

    public void update(Basket basket)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(basket.getId())) {
            if (!existsById(basket.getId())) {
                throw new ResourceNotFoundException("Cannot find Basket with id: " + basket.getId());
            }
            basketRepository.save(basket);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save basket");
            exc.addErrorMessage("Basket is null or empty");
            throw exc;
        }
    }

    public void updateItems(Long id, List<Item> items)
            throws ResourceNotFoundException {
        Basket basket = findById(id);
        basket.setItems(items);
        basketRepository.save(basket);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find basket with id: " + id);
        } else {
            basketRepository.deleteById(id);
        }
    }

    public List<Basket> getActiveBaskets() {
        List<Basket> baskets = basketRepository.getBasketsByActive(true);
        return baskets;
    }

    public List<Basket> getActiveBasketsByDate(Date startDate) {
        List<Basket> baskets = basketRepository.getBasketsByActiveAndStartDateTime(true,startDate);
        return baskets;
    }

    public Long count() {
        return basketRepository.count();
    }
}

