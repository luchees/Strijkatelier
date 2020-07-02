package com.strike.strijkatelier.mapper;


import com.strike.strijkatelier.domain.entity.Item;
import com.strike.strijkatelier.domain.model.ItemDto;
import org.springframework.stereotype.Component;


@Component
public class ItemDtoMapper {

    public ItemDtoMapper() {
    }

    public Item mapToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setMinutes(itemDto.getMinutes());
        item.setPrice(itemDto.getPrice());
        item.setItemName(itemDto.getItemName());
        item.setId(itemDto.getId());
        return item;
    }

    public ItemDto maptoItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setMinutes(item.getMinutes());
        itemDto.setPrice(item.getPrice());
        itemDto.setItemName(item.getItemName());
        itemDto.setId(item.getId());
        return itemDto;
    }
}

