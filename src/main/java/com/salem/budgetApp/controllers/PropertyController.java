package com.salem.budgetApp.controllers;

import com.salem.budgetApp.services.PropertyService;
import com.salem.budgetApp.services.dtos.PropertyDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public void setProperties(@RequestBody PropertyDto dto){
        propertyService.addProperty(dto);
    }

    @GetMapping
    public List<PropertyDto> getAllProperties(){
        return propertyService.findAllProperties();
    }

    @PutMapping
    public void updateProperties(@RequestBody PropertyDto dto){
        propertyService.updateProperty(dto);
    }

    @DeleteMapping
    public void deleteProperty(@RequestBody PropertyDto dto){
        propertyService.deleteProperty(dto);
    }
}
