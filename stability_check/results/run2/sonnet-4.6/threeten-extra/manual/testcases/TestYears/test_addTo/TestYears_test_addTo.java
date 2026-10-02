package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestYears_test_addTo {

    @Test
    public void test_addTo_zeroYearsLeavesDateUnchanged() {
        LocalDate originalDate = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Years.of(0).addTo(originalDate);
        assertEquals(LocalDate.of(2019, 1, 10), result);
    }

    @Test
    public void test_addTo_positiveYearsAdvancesDateByThatManyYears() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);
        LocalDate result = (LocalDate) Years.of(5).addTo(startDate);
        assertEquals(LocalDate.of(2024, 1, 10), result);
    }
}
