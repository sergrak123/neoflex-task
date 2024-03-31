package ru.grak.neoflextask.data;

import java.time.Month;
import java.util.List;
import java.util.Map;

public class Holidays {

    public static Map<Month, List<Integer>> getHolidayDays() {
        return Map.of(
                Month.JANUARY, List.of(1, 2, 3, 4, 5, 6, 7, 8),
                Month.FEBRUARY, List.of(23),
                Month.MARCH, List.of(8),
                Month.MAY, List.of(1, 9),
                Month.JUNE, List.of(12),
                Month.NOVEMBER, List.of(4));
    }
}
