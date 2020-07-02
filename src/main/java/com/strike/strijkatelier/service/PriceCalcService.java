package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Basket;
import com.strike.strijkatelier.domain.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalcService {

    public int calculatePrice(Basket basket){

        List<Item> items = basket.getItems();
        int price=0;
        for (Item item: items){
            if (basket.cash){
                price+=item.price;
            }
            else {
                price+=item.minutes;
            }
        }
        return price;
    }
}
