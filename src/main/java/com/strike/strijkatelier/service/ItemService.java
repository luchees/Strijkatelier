package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.Item;
import com.strike.strijkatelier.domain.model.ItemDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.mapper.ItemDtoMapper;
import com.strike.strijkatelier.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    private ItemDtoMapper mapper;

    public ItemService(ItemRepository itemRepository, ItemDtoMapper mapper) {
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    private boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public Item findById(Long id) throws ResourceNotFoundException {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            throw new ResourceNotFoundException("Cannot find Item with id: " + id);
        } else return item;
    }

    public List<ItemDto> findAll() {
        List<ItemDto> itemDtos = new ArrayList<>();
        itemRepository.findAll().forEach( item ->
                itemDtos.add(mapper.maptoItemDto(item))
        );
        return itemDtos;
    }


    public Item save(ItemDto itemDto) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(itemDto.getItemName())) {
            try {
                Item item = itemRepository.save(mapper.mapToItem(itemDto));
                return itemRepository.save(item);
            }
            catch (Exception e)
            {
                throw new ResourceAlreadyExistsException();
            }

        } else {
            BadResourceException exc = new BadResourceException("Failed to save item");
            exc.addErrorMessage("Item is null or empty");
            throw exc;
        }
    }

    public void update(ItemDto itemDto)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(itemDto.getId())) {
            if (!existsById(itemDto.getId())) {
                throw new ResourceNotFoundException("Cannot find Item with id: " + itemDto.getId());
            }
            findById(itemDto.getId());
            itemRepository.save(mapper.mapToItem(itemDto));
        } else {
            BadResourceException exc = new BadResourceException("Failed to save item");
            exc.addErrorMessage("Item is null or empty");
            throw exc;
        }
    }

    public void updatePrice(Long id, double price)
            throws ResourceNotFoundException {

        Item item = findById(id);
        item.setPrice(price);
        itemRepository.save(item);
    }
    public void updateMinutes(Long id, int minutes )
            throws ResourceNotFoundException {
        Item item = findById(id);
        item.setMinutes(minutes);
        itemRepository.save(item);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find item with id: " + id);
        } else {
            itemRepository.deleteById(id);
        }
    }

    public Long count() {
        return itemRepository.count();
    }

    public List<ItemDto> findByName(String itemName) throws ResourceNotFoundException{

            List<ItemDto> itemDtos = new ArrayList<>();
            itemRepository.findByItemName(itemName).forEach(item ->
                    itemDtos.add(mapper.maptoItemDto(item))
            );
            if (itemDtos.isEmpty()){
                throw new ResourceNotFoundException();
            }
            return itemDtos;
    }
}

