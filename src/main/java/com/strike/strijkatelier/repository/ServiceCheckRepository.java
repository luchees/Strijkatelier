package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.ServiceCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceCheckRepository extends JpaRepository<ServiceCheck, Long> {


    List<ServiceCheck> findAllByServiceCheckNumber(String serviceCheckNumber);
}