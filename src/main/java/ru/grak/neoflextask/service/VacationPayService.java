package ru.grak.neoflextask.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VacationPayService {

    BigDecimal calculateVacationPay(BigDecimal averageMonthSalary, int vacationDays);
    BigDecimal calculateVacationPay(BigDecimal averageMonthSalary, int vacationDays, LocalDate vacationStartDate);
}
