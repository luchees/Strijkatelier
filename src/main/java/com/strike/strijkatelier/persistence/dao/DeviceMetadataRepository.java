package com.strike.strijkatelier.persistence.dao;

import com.strike.strijkatelier.persistence.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByUserId(Long userId);
}
