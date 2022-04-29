package com.salem.budgetApp.services;

import com.salem.budgetApp.mappers.RoomsMapper;
import com.salem.budgetApp.repositories.RoomsRepository;
import com.salem.budgetApp.repositories.entities.RoomsEntity;
import com.salem.budgetApp.services.dtos.RoomsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final UserLogInfoService userLogInfoService;
    private final RoomsMapper roomsMapper;

    public UUID saveRoom(RoomsDto dto){
        var user = userLogInfoService.getLoggedUserEntity();
        var entity = roomsMapper.fromDtoToEntity(dto, user);
        var savedRoom = roomsRepository.save(entity);
        return savedRoom.getId();
    }

}
