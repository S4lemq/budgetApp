package com.salem.budgetApp.controllers;

import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @PostMapping
    public void setExpenses(@RequestBody ExpensesDto dto){
        throw new NotYetImplementedException();
    }

    @GetMapping
    public List<ExpensesDto> getAllExpenses(){
        throw new NotYetImplementedException();
    }

    @PutMapping
    public void updateExpenses(@RequestBody ExpensesDto dto){
        throw new NotYetImplementedException();
    }

    @DeleteMapping
    public void deleteExpenses(@RequestBody ExpensesDto dto){
        throw new NotYetImplementedException();
    }

}
