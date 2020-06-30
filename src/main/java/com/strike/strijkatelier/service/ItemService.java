package com.strike.strijkatelier.service;

import com.strike.strijkatelier.exceptions.BadResourceException;
import com.strike.strijkatelier.exceptions.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exceptions.ResourceNotFoundException;
import com.strike.strijkatelier.model.Item;
import com.strike.strijkatelier.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;


    private boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public Item findById(Long id) throws ResourceNotFoundException {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) {
            throw new ResourceNotFoundException("Cannot find Item with id: " + id);
        } else return item;
    }

    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().forEach(items::add);
        return items;
    }


    public Item save(Item item) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(item.getId())) {
            if (item.getId() != null && existsById(item.getId())) {
                throw new ResourceAlreadyExistsException("Item with id: " + item.getId() +
                        " already exists");
            }
            return itemRepository.save(item);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save item");
            exc.addErrorMessage("Item is null or empty");
            throw exc;
        }
    }

    public void update(Item item)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(item.getId())) {
            if (!existsById(item.getId())) {
                throw new ResourceNotFoundException("Cannot find Item with id: " + item.getId());
            }
            itemRepository.save(item);
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
}

