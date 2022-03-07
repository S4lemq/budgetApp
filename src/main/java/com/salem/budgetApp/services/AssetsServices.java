package com.salem.budgetApp.services;

import com.salem.budgetApp.services.dtos.AssetsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class AssetsServices {

    public AssetsDto getAssets(){
        var assetsDto = new AssetsDto();
        assetsDto.setAssets(asList(1,3,5));
        return assetsDto;
    }
}
