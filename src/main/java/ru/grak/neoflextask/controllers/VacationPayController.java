package ru.grak.neoflextask.controllers;

import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.grak.neoflextask.service.VacationPayServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api")
public class VacationPayController {

    private final VacationPayServiceImpl vacationPayService;

    @Autowired
    public VacationPayController(VacationPayServiceImpl vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate")
    public BigDecimal calculateVacationPay(@RequestParam(name = "averageSalary") @Positive BigDecimal averageSalary,
                                           @RequestParam(name = "vacationDays") @Positive int vacationDays,
                                           @RequestParam(name = "vacationStartDate", required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                   Optional<LocalDate> vacationStartDate) {

        return vacationStartDate.isPresent() ?
                vacationPayService.calculateVacationPay(averageSalary, vacationDays, vacationStartDate.get()) :
                vacationPayService.calculateVacationPay(averageSalary, vacationDays);
    }
}
