package com.strike.strijkatelier.service;

import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.domain.entity.Bucket;
import com.strike.strijkatelier.domain.entity.Item;
import com.strike.strijkatelier.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BucketService {

    @Autowired
    private BucketRepository bucketRepository;


    private boolean existsById(Long id) {
        return bucketRepository.existsById(id);
    }

    public Bucket findById(Long id) throws ResourceNotFoundException {
        Bucket bucket = bucketRepository.findById(id).orElse(null);
        if (bucket == null) {
            throw new ResourceNotFoundException("Cannot find Bucket with id: " + id);
        } else return bucket;
    }

    public List<Bucket> findAll() {
        List<Bucket> buckets = new ArrayList<>();
        bucketRepository.findAll().forEach(buckets::add);
        return buckets;
    }


    public Bucket save(Bucket bucket) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(bucket.getId())) {
            if (bucket.getId() != null && existsById(bucket.getId())) {
                throw new ResourceAlreadyExistsException("Bucket with id: " + bucket.getId() +
                        " already exists");
            }
            return bucketRepository.save(bucket);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save bucket");
            exc.addErrorMessage("Bucket is null or empty");
            throw exc;
        }
    }

    public void update(Bucket bucket)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(bucket.getId())) {
            if (!existsById(bucket.getId())) {
                throw new ResourceNotFoundException("Cannot find Bucket with id: " + bucket.getId());
            }
            bucketRepository.save(bucket);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save bucket");
            exc.addErrorMessage("Bucket is null or empty");
            throw exc;
        }
    }

    public void updateItems(Long id, List<Item> items)
            throws ResourceNotFoundException {
        Bucket bucket = findById(id);
        bucket.setItems(items);
        bucketRepository.save(bucket);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find bucket with id: " + id);
        } else {
            bucketRepository.deleteById(id);
        }
    }

    public List<Bucket> getActiveBuckets() {
        List<Bucket> buckets = bucketRepository.getBucketsByActive(true);
        return buckets;
    }

    public List<Bucket> getActiveBucketsByDate(Date startDate) {
        List<Bucket> buckets = bucketRepository.getBucketsByActiveAndStartDateTime(true,startDate);
        return buckets;
    }

    public Long count() {
        return bucketRepository.count();
    }
}

