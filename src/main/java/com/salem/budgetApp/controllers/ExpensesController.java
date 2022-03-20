package com.salem.budgetApp.controllers;

import com.salem.budgetApp.services.ExpensesService;
import com.salem.budgetApp.services.dtos.ExpensesDto;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    private final ExpensesService expensesService;

    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @PostMapping
    public void setExpenses(@RequestBody ExpensesDto dto){
        expensesService.setExpenses(dto);
    }

    @GetMapping
    public List<ExpensesDto> getAllExpenses(){
        return expensesService.getAllExpenses();
    }

    @PutMapping
    public void updateExpenses(@RequestBody ExpensesDto dto){
        expensesService.updateExpenses(dto);
    }

    @DeleteMapping
    public void deleteExpenses(@RequestBody ExpensesDto dto){
        expensesService.deleteExpenses(dto);
    }

}
