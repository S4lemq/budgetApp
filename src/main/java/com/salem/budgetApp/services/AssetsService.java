package com.salem.budgetApp.services;

import com.salem.budgetApp.mappers.AssetsMapper;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.services.dtos.AssetDto;
import com.salem.budgetApp.services.dtos.AssetsDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private AssetsRepository assetsRepository;
    private AssetsMapper assetsMapper;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
    }

    public AssetsDto getAllAssets(){
        var assetsAmount = assetsRepository.findAll().stream()
                .map(entity -> entity.getAmount().intValue())
                .collect(Collectors.toList());

        var dto = new AssetsDto();
        dto.setAssets(assetsAmount);
        return dto;
    }
    
    public void setAsset(int asset){
        var dto = new AssetDto();
        dto.setAmount(new BigDecimal(asset));
        var entity = assetsMapper.fromDtoToEntity(dto);
        assetsRepository.save(entity);
    }
}
