package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {

    List<Bucket> getBucketsByActive(Boolean active);
    List<Bucket> getBucketsByActiveAndStartDateTime(Boolean active, Date startDateTime);
}