package com.salem.budgetApp.services;

import com.salem.budgetApp.services.dtos.AssetsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class AssetsServices {
    
    private AssetsDto assetsDto = new AssetsDto();

    public AssetsDto getAssets(){
        return assetsDto;
    }
    
    public void setAsset(int asset){
        var allAssets = assetsDto.getAssets();
        if(allAssets == null){
            allAssets = new ArrayList<>();
        }
        allAssets.add(asset);
        assetsDto.setAssets(allAssets);
    }
}
