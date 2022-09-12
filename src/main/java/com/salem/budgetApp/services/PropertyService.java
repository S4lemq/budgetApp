package com.salem.budgetApp.services;

import com.salem.budgetApp.mappers.PropertyMapper;
import com.salem.budgetApp.repositories.PropertyRepository;
import com.salem.budgetApp.repositories.RoomsRepository;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.PropertyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserLogInfoService userLogInfoService;
    private final PropertyMapper propertyMapper;
    private final RoomsRepository roomsRepository;
    private final RentService rentService;

    public UUID addProperty(PropertyDto dto){
        UserEntity user = getUserEntity();
        PropertyEntity entity = propertyMapper.fromDtoToEntity(dto, user);

        var saveEntity = propertyRepository.save(entity);
        return saveEntity.getId();
    }

    public List<PropertyDto> findAllProperties(boolean isSold){
        var user = getUserEntity();
        return propertyRepository.findAllByUser(user, isSold)
                .stream()
                .map(rentService::setRentRoomInProperty)
                .map(propertyMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void setSoldProperty(UUID id){
        var user = getUserEntity();
        propertyRepository.setSoldProperty(user, id);
    }

    @Transactional
    public void updateProperty(PropertyDto dto){
        updateOnlyProperty(dto);
        updateRentRoomsInProperty(dto);
    }

    private void updateRentRoomsInProperty(PropertyDto dto) {
        rentService.setRentRooms(dto);
    }

    private void updateOnlyProperty(PropertyDto dto) {
        var entity = propertyRepository.findById(dto.getId()).stream().findFirst();
        if(entity.isPresent()){
            var entityToChange = entity.get();
            var roomsEntity = dto.getRooms().stream()
                    .map(it -> roomsRepository.findById(it.getId()).get())
                    .toList();

            propertyMapper.updateEntityByDto(entityToChange, dto, roomsEntity);
        }
    }


    private UserEntity getUserEntity(){
        return userLogInfoService.getLoggedUserEntity();
    }
}
