package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestMonths_test_subtractFrom {

    @Test
    public void test_subtractFrom_zeroMonths_doesNotChangeDate() {
        LocalDate date = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Months.of(0).subtractFrom(date);
        assertEquals(date, result);
    }

    @Test
    public void test_subtractFrom_fiveMonths_movesDateBackFiveMonths() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate expectedDate = LocalDate.of(2018, 8, 10);
        LocalDate result = (LocalDate) Months.of(5).subtractFrom(startDate);
        assertEquals(expectedDate, result);
    }
}
