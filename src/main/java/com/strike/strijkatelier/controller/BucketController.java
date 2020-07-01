package com.strike.strijkatelier.controller;

import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.model.Bucket;
import com.strike.strijkatelier.model.Item;
import com.strike.strijkatelier.service.BucketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/buckets")
public class BucketController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private  BucketService bucketService;


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Bucket>> findAll(
            @RequestParam(value="page", defaultValue="1") int pageNumber,
            @RequestParam(required=false) Long id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseEntity.ok(bucketService.findAll());
        }
        else {
            return ResponseEntity.ok(bucketService.findAll());
        }
    }

    @GetMapping(value = "/{bucketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bucket> findBucketById(@PathVariable long bucketId) {
        try {
            Bucket book = bucketService.findById(bucketId);
            return ResponseEntity.ok(book);  // return 200, with json body
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<Bucket> addBucket(@Valid @RequestBody Bucket bucket)
            throws URISyntaxException {
        try {
            Bucket newBucket = bucketService.save(bucket);
            return ResponseEntity.created(new URI("/api/buckets/" + newBucket.getId()))
                    .body(bucket);
        } catch (ResourceAlreadyExistsException ex) {
            // log exception first, then return Conflict (409)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/{bucketId}")
    public ResponseEntity<Bucket> updateBucket(@Valid @RequestBody Bucket bucket,
                                                 @PathVariable long bucketId) {
        try {
            bucket.setId(bucketId);
            bucketService.update(bucket);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{bucketId}")
    public ResponseEntity<Void> updateAddress(@PathVariable long bucketId,
                                              @RequestBody List<Item> items) {
        try {
            bucketService.updateItems(bucketId, items);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path="/{bucketId}")
    public ResponseEntity<Void> deleteBucketById(@PathVariable long bucketId) {
        try {
            bucketService.deleteById(bucketId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}