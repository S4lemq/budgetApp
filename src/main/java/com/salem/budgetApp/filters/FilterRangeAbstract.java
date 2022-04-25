package com.salem.budgetApp.filters;

import com.salem.budgetApp.enums.FilterParametersCalendarEnum;
import com.salem.budgetApp.enums.FilterSpecification;
import com.salem.budgetApp.enums.MonthsEnum;
import com.salem.budgetApp.repositories.entities.UserEntity;
import com.salem.budgetApp.validators.filter.FilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

abstract class FilterRangeAbstract<T> {

    @Autowired
    private FilterStrategy filterStrategy;

    private static final String DATE_SUFFIX = "T00:00:00.001Z";

    public List<T> getAllByFilter(Map<String, String> filter, UserEntity user, FilterSpecification specification){

        filterStrategy.checkFilterForSpecification(filter, specification);

        if(isFilterForFromToDate(filter)) {
            var fromDate = filter.get(FilterParametersCalendarEnum.FROM_DATE.getKey());
            var toDate = filter.get(FilterParametersCalendarEnum.TO_DATE.getKey());

            return getAllEntityBetweenDate(user, parseDateToInstant(fromDate), parseDateToInstant(toDate));

        }else if(isFilterForMonthYear(filter)){
            MonthsEnum month = MonthsEnum.valueOf(filter.get(FilterParametersCalendarEnum.MONTH.getKey()).toUpperCase());
            String year = filter.get(FilterParametersCalendarEnum.YEAR.getKey());
            return getAllExpensesForMonthInYear(user, month,year);
        }

        return Collections.emptyList();
    }

    private boolean isFilterForMonthYear(Map<String, String> filter) {
        return filter.containsKey(FilterParametersCalendarEnum.YEAR.getKey())
                && filter.containsKey(FilterParametersCalendarEnum.MONTH.getKey());
    }

    private boolean isFilterForFromToDate(Map<String, String> filter) {
        return filter.containsKey(FilterParametersCalendarEnum.FROM_DATE.getKey())
                && filter.containsKey(FilterParametersCalendarEnum.TO_DATE.getKey());
    }

    private List<T> getAllExpensesForMonthInYear(UserEntity user, MonthsEnum month, String year) {

        String from = month.getFirstDayForYear(year);
        String to = month.getLastDayForYear(year);

        return getAllEntityBetweenDate(user,parseDateToInstant(from),parseDateToInstant(to));
    }

    private Instant parseDateToInstant(String date) {
        return Instant.parse(date + DATE_SUFFIX);
    }

    protected abstract List<T> getAllEntityBetweenDate(UserEntity user, Instant fromDate, Instant toDate);

}
