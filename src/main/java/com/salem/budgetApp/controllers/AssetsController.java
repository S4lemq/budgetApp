package com.salem.budgetApp.controllers;

import com.salem.budgetApp.services.AssetsService;
import com.salem.budgetApp.services.dtos.AssetDto;
import com.salem.budgetApp.services.dtos.AssetsDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public void setAssets(@RequestBody AssetDto dto){
        assetsService.setAsset(dto);
    }

    @DeleteMapping
    public void deleteAsset(@RequestBody AssetDto dto){
        assetsService.deleteAsset(dto);
    }
}
