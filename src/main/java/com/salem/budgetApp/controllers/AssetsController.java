package com.salem.budgetApp.controllers;

import com.salem.budgetApp.services.AssetsServices;
import com.salem.budgetApp.services.dtos.AssetsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsServices assetsServices;

    public AssetsController(AssetsServices assetsServices) {
        this.assetsServices = assetsServices;
    }

    @GetMapping
    public AssetsDto getAssets(){
        return assetsServices.getAssets();
    }

    @PostMapping("/{asset}")
    public void setAssets(@PathVariable("asset") int asset){
        assetsServices.setAsset(asset);
    }
}
