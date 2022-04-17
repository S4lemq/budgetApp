package com.salem.budgetApp.controllers;

import com.salem.budgetApp.enums.AssetCategory;
import com.salem.budgetApp.services.AssetsService;
import com.salem.budgetApp.services.dtos.AssetDto;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    public AssetsController(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    @GetMapping
    public List<AssetDto> getAssets(){
        return assetsService.getAllAssets();
    }

    @PostMapping
    public void setAsset(@RequestBody AssetDto dto){
        assetsService.setAsset(dto);
    }

    @DeleteMapping
    public void deleteAsset(@RequestBody AssetDto dto){
        assetsService.deleteAsset(dto);
    }

    @PutMapping
    public void updateAsset(@RequestBody AssetDto dto){
        assetsService.updateAsset(dto);
    }

    @GetMapping("/find")
    public List<AssetDto> getAllAssetsByCategory(@PathParam("category") String category){
        return assetsService.getAssetByCategory(AssetCategory.valueOf(category.toUpperCase()));
    }

    @GetMapping("/filter")
    public List<AssetDto> getFilteredAsset(@RequestParam Map<String, String> filter){
        return assetsService.getAssetsByFilter(filter);
    }
}
