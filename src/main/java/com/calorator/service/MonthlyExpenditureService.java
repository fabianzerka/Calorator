package com.calorator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface MonthlyExpenditureService {
    void calculateMonthlySpending(Long userId, LocalDate month);

    void setCustomLimit(Long userId, BigDecimal customLimit);

    BigDecimal getMonthlyExpenditure(Long userId, LocalDate month);

    Map<Long, BigDecimal> findUsersExceedingLimitForPreviousMonth();
}
