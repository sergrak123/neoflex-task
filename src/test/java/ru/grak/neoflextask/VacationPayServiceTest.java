package ru.grak.neoflextask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.grak.neoflextask.service.VacationPayServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VacationPayServiceTest {

	@Autowired
	private VacationPayServiceImpl vacationPayService;

	@Test
	public void testCalculateVacationPay() {
		BigDecimal averageMonthSalary = BigDecimal.valueOf(80000);
		int vacationDays = 11;
		BigDecimal expectedPay = BigDecimal.valueOf(30034.1294);

		BigDecimal actualPay = vacationPayService.calculateVacationPay(averageMonthSalary, vacationDays);

		assertEquals(expectedPay, actualPay);
	}

	@Test
	public void testCalculateVacationPayWithStartDateAndWeekend() {
		BigDecimal averageMonthSalary = BigDecimal.valueOf(80000);
		int vacationDays = 11;

		LocalDate vacationStartDate = LocalDate.of(2024, 4, 1);
		BigDecimal expectedPay = BigDecimal.valueOf(24573.3786);

		BigDecimal actualPay = vacationPayService.calculateVacationPay(averageMonthSalary, vacationDays, vacationStartDate);

		assertEquals(expectedPay, actualPay);
	}

	@Test
	public void testCalculateVacationPayWithStartDateAndHolidays() {
		BigDecimal averageMonthSalary = BigDecimal.valueOf(80000);
		int vacationDays = 5;

		LocalDate vacationStartDate = LocalDate.of(2024, 5, 6);
		BigDecimal expectedPay = BigDecimal.valueOf(10921.5016);

		BigDecimal actualPay = vacationPayService.calculateVacationPay(averageMonthSalary, vacationDays, vacationStartDate);

		assertEquals(expectedPay, actualPay);
	}
}

