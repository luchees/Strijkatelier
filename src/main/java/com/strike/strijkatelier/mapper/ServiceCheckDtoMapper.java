package com.strike.strijkatelier.mapper;

import com.strike.strijkatelier.domain.entity.ServiceCheck;
import com.strike.strijkatelier.domain.model.ServiceCheckDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceCheckDtoMapper {

    @Autowired
    BasketDtoMapper mapper;

    public ServiceCheckDtoMapper() {
    }

    public ServiceCheck maptoServiceCheck(ServiceCheckDto serviceCheckDto) {
        ServiceCheck serviceCheck = new ServiceCheck();
        serviceCheck.setId(serviceCheckDto.getId());
        serviceCheck.setExpiryDate(serviceCheckDto.getExpiryDate());
        serviceCheck.setServiceCheckNumber(serviceCheckDto.getServiceCheckNumber());
        serviceCheck.setSigned(serviceCheckDto.isSigned());
        serviceCheck.setUsedDate(serviceCheckDto.getUsedDate());
        serviceCheck.setBasket(mapper.mapToBasket(serviceCheckDto.getBasketDto()));
        return serviceCheck;
    }

    public ServiceCheckDto mapToServiceCheckDto(ServiceCheck serviceCheck) {
        ServiceCheckDto serviceCheckDto = new ServiceCheckDto();
        serviceCheckDto.setId(serviceCheck.getId());
        serviceCheckDto.setExpiryDate(serviceCheck.getExpiryDate());
        serviceCheckDto.setServiceCheckNumber(serviceCheck.getServiceCheckNumber());
        serviceCheckDto.setSigned(serviceCheck.isSigned());
        serviceCheckDto.setUsedDate(serviceCheck.getUsedDate());
        serviceCheckDto.setBasketDto(mapper.mapToBasketDto(serviceCheck.getBasket()));
        return serviceCheckDto;
    }

}
