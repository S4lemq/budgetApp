package com.salem.budgetApp.services;

import com.salem.budgetApp.repositories.PropertyRoomAssociationsRepository;
import com.salem.budgetApp.repositories.entities.PropertyEntity;
import com.salem.budgetApp.repositories.entities.PropertyRoomAssociationsEntity;
import com.salem.budgetApp.repositories.entities.RoomsEntity;
import com.salem.budgetApp.services.dtos.PropertyDto;
import com.salem.budgetApp.services.dtos.RoomsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentService {
    private final PropertyRoomAssociationsRepository associationsRepository;

    public PropertyEntity setRentRoomInProperty(PropertyEntity propertyEntity){
        Optional<List<PropertyRoomAssociationsEntity>> optionalPropertyRoom =
                associationsRepository.getAssociationsByPropertyId(propertyEntity.getId());

        if(optionalPropertyRoom.isPresent()){
            List<PropertyRoomAssociationsEntity> associationsEntities = optionalPropertyRoom.get();
            propertyEntity.getRooms()
                    .forEach(room -> setRentForRoom(room, associationsEntities));
        }
        return propertyEntity;
    }

    public void setRentRooms(PropertyDto dto){
        UUID propertyId = dto.getId();
        List<RoomsDto> roomsList = dto.getRooms();

        roomsList.forEach(
                room -> associationsRepository.setRent(propertyId, room.getId(), setRentValueOrFalse(room.getRent()))
        );
    }

    private Boolean setRentValueOrFalse(Boolean isRoom) {
        return Objects.nonNull(isRoom) ? isRoom : false;
    }

    private RoomsEntity setRentForRoom(RoomsEntity room,
                                List<PropertyRoomAssociationsEntity> associationsEntities) {
        PropertyRoomAssociationsEntity propertyRoomAssociationsEntity = associationsEntities.stream()
                .filter(it -> it.getRoomId().equals(room.getId()))
                .findFirst()
                .get();

        room.setRent(propertyRoomAssociationsEntity.getRent());
        return room;
    }
}
