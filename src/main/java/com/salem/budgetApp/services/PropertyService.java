package com.salem.budgetApp.services;

import com.salem.budgetApp.mappers.PropertyMapper;
import com.salem.budgetApp.repositories.PropertyRepository;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.PropertyDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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
        UserEntity user = getUserEntity();
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
        var user = getUserEntity();
        return propertyRepository.findAllByUser(user)
                .stream()
                .map(entity -> propertyMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public void deleteProperty(PropertyDto dto){
        var user = getUserEntity();
        var entity = propertyMapper.fromDtoToEntity(dto, user);
        propertyRepository.delete(entity);
    }

    @Transactional
    public void updateProperty(PropertyDto dto){
        var entity = propertyRepository.findById(dto.getId());
        if(entity.isPresent()){
            updateProperty(entity.get(), dto);
        }
    }

    private void updateProperty(PropertyEntity entity, PropertyDto dto){
        if(Objects.nonNull(dto.getRooms()) && !dto.getRooms().equals(entity.getRooms())){
            entity.setRooms(dto.getRooms());
        }
        if(Objects.nonNull(dto.getSingle()) && !dto.getSingle().equals(entity.getSingle())){
            entity.setSingle(dto.getSingle());
        }
        if(Objects.nonNull(dto.getCity()) && !dto.getCity().equals(entity.getCity())){
            entity.setCity(dto.getCity());
        }
        if(Objects.nonNull(dto.getPostCode()) && !dto.getPostCode().equals(entity.getPostCode())){
            entity.setPostCode(dto.getPostCode());
        }
        if(Objects.nonNull(dto.getSingle()) && !dto.getStreet().equals(entity.getStreet())){
            entity.setStreet(dto.getStreet());
        }
        if(Objects.nonNull(dto.getHouse()) && !dto.getHouse().equals(entity.getHouse())){
            entity.setHouse(dto.getHouse());
        }
    }

    private UserEntity getUserEntity(){
        return userLogInfoService.getLoggedUserEntity();
    }
}
