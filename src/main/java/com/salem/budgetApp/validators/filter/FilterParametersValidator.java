package com.salem.budgetApp.validators.filter;

import com.salem.budgetApp.enums.FilterParametersCalendarEnum;

import java.util.Map;

abstract class FilterParametersValidator {

    public void asserFilter(Map<String, String> filter){
        checkIfMonthExistYearMissing(filter, "601D31F212E246F5AFAC5D607EB95312");
        checkIfYearExistMonthMissing(filter, "CA7CF6E80FCD434BA132A28CB91C449C");
        checkIfFromDateExistToDateMissing(filter, "375C506B154A434F9C8F97155D1F2CA7");
        checkIfToDateExistFromDateMissing(filter, "06B1040B400C4B56ADCD649F764B9894");
    }

    private void checkIfToDateExistFromDateMissing(Map<String, String> filter, String errorCode) {
        if(filter.containsKey(FilterParametersCalendarEnum.TO_DATE.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.FROM_DATE.getKey())){
            throwException(FilterParametersCalendarEnum.FROM_DATE.getKey(),errorCode);
        }
    }

    private void checkIfFromDateExistToDateMissing(Map<String, String> filter, String errorCode) {
        if(filter.containsKey(FilterParametersCalendarEnum.FROM_DATE.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.TO_DATE.getKey())){
            throwException(FilterParametersCalendarEnum.TO_DATE.getKey(),errorCode);
        }
    }

    private void checkIfYearExistMonthMissing(Map<String, String> filter, String errorCode) {
        if(filter.containsKey(FilterParametersCalendarEnum.YEAR.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.MONTH.getKey())){
            throwException(FilterParametersCalendarEnum.MONTH.getKey(), errorCode);
        }
    }

    private void checkIfMonthExistYearMissing(Map<String, String> filter, String errorCode) {

        if(filter.containsKey(FilterParametersCalendarEnum.MONTH.getKey())
                && !filter.containsKey(FilterParametersCalendarEnum.YEAR.getKey())){
            throwException(FilterParametersCalendarEnum.YEAR.getKey(), errorCode);
        }

    }

    public abstract void throwException(String missingKey, String errorCode);
}
