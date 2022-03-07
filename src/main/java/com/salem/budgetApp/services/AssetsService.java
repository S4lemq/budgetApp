package com.salem.budgetApp.services;

import com.salem.budgetApp.mappers.AssetsMapper;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.services.dtos.AssetDto;
import com.salem.budgetApp.services.dtos.AssetsDto;
import com.salem.budgetApp.validators.AssetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private AssetsRepository assetsRepository;
    private AssetsMapper assetsMapper;
    private AssetValidator assetValidator;

    public AssetsService(AssetsRepository assetsRepository, AssetsMapper assetsMapper, AssetValidator assetValidator) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
        this.assetValidator = assetValidator;
    }

    public AssetsDto getAllAssets(){
        var assetsAmount = assetsRepository.findAll().stream()
                .map(entity -> entity.getAmount().intValue())
                .collect(Collectors.toList());

        var dto = new AssetsDto();
        dto.setAssets(assetsAmount);
        return dto;
    }
    
    public void setAsset(AssetDto dto){
        assetValidator.validate(dto);
        var entity = assetsMapper.fromDtoToEntity(dto);
        assetsRepository.save(entity);
    }

    public void deleteAsset(AssetDto dto) {
        var entity = assetsMapper.fromDtoToEntity(dto);
        assetsRepository.delete(entity);
    }
}
