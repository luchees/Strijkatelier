package com.strike.strijkatelier.mapper;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.domain.model.BasketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BasketDtoMapper {

    @Autowired
    ItemDtoMapper ItemDtomapper;
    @Autowired
    CustomerDtoMapper customerDtoMapper;

    public BasketDtoMapper() {
    }

    public Basket mapToBasket(BasketDto basketDto) {
        Basket basket = new Basket();
        basket.setId(basketDto.getId());
        basket.setActive(basketDto.isActive());
        basket.setItems(
                basketDto.getItemDtos().stream()
                        .map(ItemDtomapper::mapToItem)
                        .collect(Collectors.toList()));
        basket.setCash(basketDto.isCash());
        basket.setCustomer(customerDtoMapper.mapToCustomer(basketDto.getCustomerDto()));
        basket.setStartDateTime(basketDto.getStartDateTime());
        basket.setPrice(basketDto.getPrice());
        basket.setDoneDateTime(basketDto.getDoneDateTime());
        return basket;
    }

    public BasketDto mapToBasketDto(Basket basket) {
        BasketDto basketDto = new BasketDto();
        basketDto.setId(basket.getId());
        basketDto.setActive(basket.isActive());
        basketDto.setItemDtos(basket.getItems().stream()
                .map(ItemDtomapper::maptoItemDto)
                .collect(Collectors.toList()));
        basketDto.setCash(basket.isCash());
        basketDto.setCustomerDto(customerDtoMapper.mapToCustomerDto(basket.getCustomer()));
        basketDto.setStartDateTime(basket.getStartDateTime());
        basketDto.setPrice(basket.getPrice());
        basketDto.setDoneDateTime(basket.getDoneDateTime());
        return basketDto;
    }
}
