package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestMonths_test_addTo {

    @Test
    public void test_addTo_zeroMonths_returnsUnchangedDate() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Months.of(0).addTo(startDate);
        assertEquals(startDate, result);
    }

    @Test
    public void test_addTo_fiveMonths_advancesDateByFiveMonths() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate expectedDate = LocalDate.of(2019, 6, 10);
        LocalDate result = (LocalDate) Months.of(5).addTo(startDate);
        assertEquals(expectedDate, result);
    }
}
