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

    /** Среднее количество дней в месяце */
    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;

    /**
     * Рассчитывает оплату за отпуск на основе средней заработной платы за месяц
     * и количества дней отпуска.
     *
     * @param averageMonthSalary средняя заработная плата за месяц
     * @param vacationDays количество дней отпуска
     * @return рассчитанная оплата за отпуск
     */
    @Override
    public BigDecimal calculateVacationPay(BigDecimal averageMonthSalary, int vacationDays) {

        BigDecimal averageDaySalary = averageMonthSalary
                .divide(BigDecimal.valueOf(AVERAGE_DAYS_IN_MONTH), 4, RoundingMode.HALF_UP);

        return averageDaySalary.multiply(BigDecimal.valueOf(vacationDays));
    }

    /**
     * Рассчитывает оплату за отпуск на основе средней заработной платы за месяц,
     * количества дней отпуска и даты начала отпуска.
     *
     * @param averageMonthSalary средняя заработная плата за месяц
     * @param vacationDays количество дней отпуска
     * @param vacationStartDate дата начала отпуска
     * @return рассчитанная оплата за отпуск
     */
    @Override
    public BigDecimal calculateVacationPay(BigDecimal averageMonthSalary, int vacationDays, LocalDate vacationStartDate) {

        int totalVacationDays = calculatePaidDays(vacationDays, vacationStartDate);

        return calculateVacationPay(averageMonthSalary, totalVacationDays);
    }

    /**
     * Рассчитывает общее количество оплачиваемых дней отпуска на основе
     * количества дней отпуска и даты начала отпуска, исключая праздничные и выходные дни.
     *
     * @param vacationDays количество дней отпуска
     * @param vacationStartDate дата начала отпуска
     * @return общее количество оплачиваемых дней отпуска
     */
    private int calculatePaidDays(int vacationDays, LocalDate vacationStartDate) {

        List<LocalDate> vacationDaysWithHolidays = Stream
                .iterate(vacationStartDate, nextVacationDate -> nextVacationDate.plusDays(1)).limit(vacationDays)
                .filter(vacationDate -> !isHoliday(vacationDate))
                .filter(vacationDate -> !(vacationDate.getDayOfWeek() == DayOfWeek.SATURDAY || vacationDate.getDayOfWeek() == DayOfWeek.SUNDAY))
                .collect(Collectors.toList());

        return vacationDaysWithHolidays.size();
    }

    /**
     * Проверяет, является ли заданная дата праздничным днем.
     *
     * @param date дата для проверки
     * @return true, если дата является праздничным днем, в противном случае - false
     */
    private boolean isHoliday(LocalDate date){
        var holidayDays = getHolidayDays();

        return holidayDays.containsKey(date.getMonth()) &&
                holidayDays.get(date.getMonth()).contains(date.getDayOfMonth());
    }

}
