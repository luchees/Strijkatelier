package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.domain.model.BasketDto;
import com.strike.strijkatelier.domain.model.ItemDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.mapper.BasketDtoMapper;
import com.strike.strijkatelier.mapper.ItemDtoMapper;
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

    @Autowired
    private BasketDtoMapper mapper;
    @Autowired
    private ItemDtoMapper itemDtoMapper;

    private boolean existsById(Long id) {
        return basketRepository.existsById(id);
    }

    public BasketDto findById(Long id) throws ResourceNotFoundException {
        Basket basket = basketRepository.findById(id).orElse(null);
        if (basket == null) {
            throw new ResourceNotFoundException("Cannot find Basket with id: " + id);
        } else return mapper.mapToBasketDto(basket);
    }

    public List<BasketDto> findAll() {
        List<BasketDto> basketDtos = new ArrayList<>();
        List<Basket> baskets = basketRepository.findAll();

        if (!baskets.isEmpty()) {
            baskets.forEach(basket ->
                    basketDtos.add(mapper.mapToBasketDto(basket))
            );
        }

        return basketDtos;
    }


    public BasketDto save(BasketDto basketDto) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(basketDto.getId())) {
            if (basketDto.getId() != null && existsById(basketDto.getId())) {
                throw new ResourceAlreadyExistsException("Basket with id: " + basketDto.getId() +
                        " already exists");
            }
            Basket basket = mapper.mapToBasket(basketDto);
            return mapper.mapToBasketDto(basketRepository.save(basket));
        } else {
            BadResourceException exc = new BadResourceException("Failed to save basket");
            exc.addErrorMessage("Basket is null or empty");
            throw exc;
        }
    }

    public BasketDto updateItems(long basketId, List<ItemDto> itemDtos) throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(basketId)) {
            if (!existsById(basketId)) {
                throw new ResourceNotFoundException("Cannot find Basket with id: " + basketId);
            }
            BasketDto basketDto = findById(basketId);
            basketDto.addItemDtos(itemDtos);
            basketRepository.save(mapper.mapToBasket(basketDto));
            return basketDto;
        } else {
            BadResourceException exc = new BadResourceException("Failed to save basket");
            exc.addErrorMessage("Basket is null or empty");
            throw exc;
        }


    }

    public void update(BasketDto basketDto)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(basketDto.getId())) {
            if (!existsById(basketDto.getId())) {
                throw new ResourceNotFoundException("Cannot find Basket with id: " + basketDto.getId());
            }
            Basket basket = mapper.mapToBasket(basketDto);
            basketRepository.save(basket);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save basket");
            exc.addErrorMessage("Basket is null or empty");
            throw exc;
        }
    }

    public void setItems(Long basketId, List<ItemDto> itemDtos)
            throws ResourceNotFoundException {
        BasketDto basketDto = findById(basketId);
        basketDto.setItemDtos(itemDtos);
        basketRepository.save(mapper.mapToBasket(basketDto));
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find basket with id: " + id);
        } else {
            basketRepository.deleteById(id);
        }
    }

    public List<BasketDto> getActiveBaskets() {
        List<BasketDto> basketDtos = new ArrayList<>();
        basketRepository.getBasketsByActive(true).forEach(basket ->
                basketDtos.add(mapper.mapToBasketDto(basket))
        );
        return basketDtos;
    }

    public List<BasketDto> getActiveBasketsByDate(Date startDate) {
        List<BasketDto> basketDtos = new ArrayList<>();
        basketRepository.getBasketsByActiveAndStartDateTime(true, startDate).forEach(basket ->
                basketDtos.add(mapper.mapToBasketDto(basket))
        );
        return basketDtos;
    }

    public Long count() {
        return basketRepository.count();
    }
}

