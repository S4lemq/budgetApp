package com.salem.budgetApp.services;

import com.salem.budgetApp.mappers.PropertyMapper;
import com.salem.budgetApp.repositories.PropertyRepository;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.PropertyDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserLogInfoService userLogInfoService;
    private final PropertyMapper propertyMapper;

    public PropertyService(PropertyRepository propertyRepository, UserLogInfoService userLogInfoService, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.userLogInfoService = userLogInfoService;
        this.propertyMapper = propertyMapper;
    }

    public UUID addProperty(PropertyDto dto){
        UserEntity user = userLogInfoService.getLoggedUserEntity();
        PropertyEntity entity = new PropertyEntity();

        entity.setUser(user);
        entity.setRooms(dto.getRooms());
        entity.setStreet(dto.getStreet());
        entity.setSingle(dto.getSingle());
        entity.setHouse(dto.getHouse());
        entity.setCity(dto.getCity());
        entity.setPostCode(dto.getPostCode());

        var saveEntity = propertyRepository.save(entity);
        return saveEntity.getId();

    }

    public List<PropertyDto> findAllProperties(){
        var user = userLogInfoService.getLoggedUserEntity();
        return propertyRepository.findAllByUser(user)
                .stream()
                .map(entity -> propertyMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }
}
