package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Bucket;
import com.strike.strijkatelier.domain.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalcService {

    public int calculatePrice(Bucket bucket){

        List<Item> items = bucket.getItems();
        int price=0;
        for (Item item: items){
            if (bucket.cash){
                price+=item.price;
            }
            else {
                price+=item.minutes;
            }
        }
        return price;
    }
}
