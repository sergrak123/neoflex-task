package ru.grak.neoflextask.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.grak.neoflextask.data.Holidays.getHolidayDays;

@Service
public class VacationPayServiceImpl implements VacationPayService {

    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;

    @Override
    public BigDecimal calculateVacationPay(BigDecimal averageMonthSalary, int vacationDays) {

        BigDecimal averageDaySalary = averageMonthSalary
                .divide(BigDecimal.valueOf(AVERAGE_DAYS_IN_MONTH), 2, RoundingMode.HALF_EVEN);

        return averageDaySalary.multiply(BigDecimal.valueOf(vacationDays));
    }

    @Override
    public BigDecimal calculateVacationPay(BigDecimal averageMonthSalary, int vacationDays, LocalDate vacationStartDate) {

        int totalVacationDays = calculatePaidDays(vacationDays, vacationStartDate);

        return calculateVacationPay(averageMonthSalary, totalVacationDays);
    }

    private int calculatePaidDays(int vacationDays, LocalDate vacationStartDate) {

        List<LocalDate> vacationDaysWithHolidays = Stream
                .iterate(vacationStartDate, nextVacationDate -> nextVacationDate.plusDays(1)).limit(vacationDays)
                .filter(vacationDate -> !isHoliday(vacationDate))
                .filter(vacationDate -> !(vacationDate.getDayOfWeek() == DayOfWeek.SATURDAY || vacationDate.getDayOfWeek() == DayOfWeek.SUNDAY))
                .collect(Collectors.toList());

        return vacationDaysWithHolidays.size();
    }

    private boolean isHoliday(LocalDate date){
        var holidayDays = getHolidayDays();

        return holidayDays.containsKey(date.getMonth()) &&
                holidayDays.get(date.getMonth()).contains(date.getDayOfMonth());
    }

}
