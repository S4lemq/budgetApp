package com.salem.budgetApp.controllers;

import com.salem.budgetApp.services.AssetsService;
import com.salem.budgetApp.services.dtos.AssetsDto;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    public AssetsController(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    @GetMapping
    public AssetsDto getAssets(){
        return assetsService.getAllAssets();
    }

    @PostMapping("/{asset}")
    public void setAssets(@PathVariable("asset") int asset){
        assetsService.setAsset(asset);
    }
}
