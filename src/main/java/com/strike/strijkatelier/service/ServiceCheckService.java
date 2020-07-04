package com.strike.strijkatelier.service;

import com.strike.strijkatelier.domain.entity.ServiceCheck;
import com.strike.strijkatelier.domain.model.ServiceCheckDto;
import com.strike.strijkatelier.exception.BadResourceException;
import com.strike.strijkatelier.exception.ResourceAlreadyExistsException;
import com.strike.strijkatelier.exception.ResourceNotFoundException;
import com.strike.strijkatelier.mapper.ServiceCheckDtoMapper;
import com.strike.strijkatelier.repository.ServiceCheckRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCheckService {

    private ServiceCheckRepository serviceCheckRepository;

    private ServiceCheckDtoMapper mapper;

    public ServiceCheckService(ServiceCheckRepository serviceCheckRepository, ServiceCheckDtoMapper mapper) {
        this.serviceCheckRepository = serviceCheckRepository;
        this.mapper = mapper;
    }

    private boolean existsById(Long id) {
        return serviceCheckRepository.existsById(id);
    }

    public ServiceCheck findById(Long id) throws ResourceNotFoundException {
        ServiceCheck serviceCheck = serviceCheckRepository.findById(id).orElse(null);
        if (serviceCheck == null) {
            throw new ResourceNotFoundException("Cannot find ServiceCheck with id: " + id);
        } else return serviceCheck;
    }

    public List<ServiceCheckDto> findAll() {
        List<ServiceCheckDto> serviceCheckDtos = new ArrayList<>();
        serviceCheckRepository.findAll().forEach( serviceCheck ->
                serviceCheckDtos.add(mapper.mapToServiceCheckDto(serviceCheck))
        );
        return serviceCheckDtos;
    }


    public ServiceCheck save(ServiceCheckDto serviceCheckDto) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(serviceCheckDto.getId())) {
            try {
                ServiceCheck serviceCheck = serviceCheckRepository.save(mapper.maptoServiceCheck(serviceCheckDto));
                return serviceCheckRepository.save(serviceCheck);
            }
            catch (Exception e)
            {
                throw new ResourceAlreadyExistsException();
            }

        } else {
            BadResourceException exc = new BadResourceException("Failed to save serviceCheck");
            exc.addErrorMessage("ServiceCheck is null or empty");
            throw exc;
        }
    }

    public void update(ServiceCheckDto serviceCheckDto)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(serviceCheckDto.getId())) {
            if (!existsById(serviceCheckDto.getId())) {
                throw new ResourceNotFoundException("Cannot find ServiceCheck with id: " + serviceCheckDto.getId());
            }
            ServiceCheck serviceCheck = serviceCheckRepository.save(mapper.maptoServiceCheck(serviceCheckDto));
            serviceCheckRepository.save(serviceCheck);
        } else {
            BadResourceException exc = new BadResourceException("Failed to save serviceCheck");
            exc.addErrorMessage("ServiceCheck is null or empty");
            throw exc;
        }
    }


    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find serviceCheck with id: " + id);
        } else {
            serviceCheckRepository.deleteById(id);
        }
    }

    public Long count() {
        return serviceCheckRepository.count();
    }

    public List<ServiceCheckDto> findByServiceCheckNumber(String serviceCheckNumber) throws ResourceNotFoundException{

            List<ServiceCheckDto> serviceCheckDtos = new ArrayList<>();
            serviceCheckRepository.findAllByServiceCheckNumber(serviceCheckNumber).forEach(serviceCheck ->
                    serviceCheckDtos.add(mapper.mapToServiceCheckDto(serviceCheck))
            );
            if (serviceCheckDtos.isEmpty()){
                throw new ResourceNotFoundException();
            }
            return serviceCheckDtos;
    }
}

