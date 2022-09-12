package com.salem.budgetApp.services;

import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.enums.FilterSpecification;
import com.salem.budgetApp.filters.FilterRangeStrategy;
import com.salem.budgetApp.mappers.AssetsMapper;
import com.salem.budgetApp.repositories.AssetsRepository;
import com.salem.budgetApp.repositories.entities.AssetEntity;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.services.dtos.AssetDto;
import com.salem.budgetApp.validators.AssetValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsService.class.getName());

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final AssetValidator assetValidator;
    private final UserLogInfoService userLogInfoService;
    private final FilterRangeStrategy<AssetEntity> filterRangeStrategy;

    public AssetsService(AssetsRepository assetsRepository,
                         AssetsMapper assetsMapper,
                         AssetValidator assetValidator,
                         UserLogInfoService userLogInfoService,
                         FilterRangeStrategy filterRangeStrategy) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
        this.assetValidator = assetValidator;
        this.userLogInfoService = userLogInfoService;
        this.filterRangeStrategy = filterRangeStrategy;
    }

    public List<AssetDto> getAllAssets(){
        LOGGER.debug("Get all assets");
        var user = getUserEntity();

        return assetsRepository.getAssetEntitiesByUser(user).stream()
                .map(entity -> assetsMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    @Transactional
    public void setAsset(List<AssetDto> dtos){
        LOGGER.info("Set Asset");
        UserEntity user = getUserEntity();
        dtos.forEach(dto -> {
            assetValidator.validate(dto);
            var entity = assetsMapper.fromDtoToEntity(dto, user);
            assetsRepository.save(entity);
        });
    }

    public void deleteAsset(AssetDto dto) {
        LOGGER.info("Delete asset");
        LOGGER.debug("AssetDto: " + dto);
        UserEntity user = getUserEntity();
        var entity = assetsMapper.fromDtoToEntity(dto, user);
        assetsRepository.delete(entity);
        LOGGER.info("Asset deleted");
    }

    public void deleteAllAssetsByUser(UserEntity userEntity){
        assetsRepository.deleteAllByUser(userEntity);
        LOGGER.info("All assets by User" + userEntity +  " deleted");
    }

    public void updateAsset(AssetDto dto) {
        LOGGER.info("Update asset");
        LOGGER.debug("AssetDto: " + dto);
        var entity = assetsRepository.findById(dto.getId());
        entity.ifPresent(e -> {
            e.setAmount(dto.getAmount());
            assetsRepository.saveAndFlush(e);
        });
        LOGGER.info("Asset update");
    }

    public List<AssetDto> getAssetByCategory(AssetCategory category){
        return assetsRepository.getAssetEntitiesByCategory(category).stream()
                .map(entity -> assetsMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public List<AssetDto> getAssetsByFilter(Map<String, String> filter) {
        var user = userLogInfoService.getLoggedUserEntity();
        FilterSpecification specification = FilterSpecification.FOR_ASSETS;

        return filterRangeStrategy.getFilteredDataForSpecification(filter, specification, user)
                .stream()
                .map(entity -> assetsMapper.fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    private UserEntity getUserEntity() {
        LOGGER.info("Get Logged User Entity");
        return userLogInfoService.getLoggedUserEntity();
    }


}
