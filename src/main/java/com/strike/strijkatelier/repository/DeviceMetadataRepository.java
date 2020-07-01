package com.strike.strijkatelier.repository;

import com.strike.strijkatelier.domain.entity.DeviceMetadata;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByUserId(Long userId);
}
