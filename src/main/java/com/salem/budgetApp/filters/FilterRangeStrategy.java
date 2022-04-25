package com.salem.budgetApp.filters;

import com.salem.budgetApp.enums.FilterSpecification;
import com.salem.budgetApp.repositories.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FilterRangeStrategy<T> {

    private final Map<String, FilterRangeAbstract> allFilterRange;

    public FilterRangeStrategy(Map<String, FilterRangeAbstract> allFilterRange) {
        this.allFilterRange = allFilterRange;
    }

    public List<T> getFilteredDataForSpecification(Map<String, String> filters,
                                                   FilterSpecification specification,
                                                   UserEntity user){
        return allFilterRange.get(specification.getForRange())
                .getAllByFilter(filters, user, specification);
    }

}
